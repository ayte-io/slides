property :index

actions :apply

action :apply do
  path = "#{Chef::Config[:file_cache_path]}/cookbooks/peio_worker/files/index-settings.json"
  content = ::File.read(path)

  package 'curl'

  execute 'health-check' do
    command(['curl', 'http://localhost:9200/_cluster/state'])
    # two and half minutes
    retries 50
    retry_delay 3
  end

  execute 'create-index' do
    command(['curl', '-XPUT', "http://localhost:9200/#{new_resource.index}"])
    ignore_failure true
  end

  # http_request thrown errors
  execute 'index settings' do
    command([
        'curl',
        '-XPUT',
        '-d',
        "#{content}",
        '-H',
        'Content-Type: application/json',
        "http://localhost:9200/#{new_resource.index}/_settings"
    ])
  end
end
