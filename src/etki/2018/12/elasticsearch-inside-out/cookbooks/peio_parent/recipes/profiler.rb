directory node['peio']['paths']['profiler'] do
  recursive true
end

archive_path = "#{node['peio']['paths']['profiler']}/archive.tar.gz"
extracted_path = "#{node['peio']['paths']['profiler']}/extracted"

remote_file archive_path do
  source node['peio']['links']['profiler']
  not_if {
    ::File.exist? archive_path
  }
end

directory extracted_path do
  recursive true
end

execute 'extract profiler' do
  command([
      'tar',
      '-xzvf',
      archive_path,
      '-C',
      extracted_path
  ])
  only_if {
    !::Dir.exist?(extracted_path) or ::Dir.entries(extracted_path).size < 3
  }
end
