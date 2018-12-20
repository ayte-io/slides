actions :launch

attribute :name, String, name_attribute: true

action :launch do
  parent = node['peio_worker']['paths']['elasticsearch']['profile']
  path = "#{parent}/#{new_resource.name}.svg"

  directory parent do
    recursive true
  end

  execute "nohup perf.service #{path} &"
end
