node['peio']['elasticsearch']['indices'].each do |index|
  if ::Dir.exist? node['peio']['paths']['data']
    peio_worker_index_settings 'index settings' do
      index index
    end

    execute "index ${index} set refresh_interval to -1" do
      command([
          'curl',
          '-XPUT',
          '-H',
          'Content-Type: application/json',
          "http://localhost:9200/#{index}/_settings",
          '-d',
          '{"refresh_interval": "-1"}'
      ])
    end

    ::Dir.entries(node['peio']['paths']['data']).each do |file|
      next if file == '.' or file == '..'
      execute "index #{index} chunk #{file} import" do
        command([
            'curl',
            '-XPOST',
            '-H',
            'Content-Type: application/json',
            "http://localhost:9200/#{index}/_bulk",
            '--data-binary',
            "@#{node['peio']['paths']['data']}/#{file}"
        ])
      end
    end

    execute "index ${index} set refresh_interval to 30s" do
      command([
          'curl',
          '-XPUT',
          '-H',
          'Content-Type: application/json',
          "http://localhost:9200/#{index}/_settings",
          '-d',
          '{"refresh_interval": "30s"}'
      ])
    end

    execute "index #{index} optimization" do
      command(['curl', '-XPOST', "http://localhost:9200/#{index}/_forcemerge?max_num_segments=1"])
    end
  end
end
