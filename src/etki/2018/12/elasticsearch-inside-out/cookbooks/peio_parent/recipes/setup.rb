package 'gzip'

directory node['peio']['paths']['data'] do
  recursive true
end

remote_file node['peio']['paths']['source'] do
  source node['peio']['links']['data']
  not_if {
    ::File.exist? node['peio']['paths']['source']
  }
end

execute 'extract dump' do
  command "zcat #{node['peio']['paths']['source']} | split -a 10 -l 500 - #{node['peio']['paths']['data']}/"
  not_if do
    ::Dir.entries(node['peio']['paths']['data']).size > 2
  end
end
