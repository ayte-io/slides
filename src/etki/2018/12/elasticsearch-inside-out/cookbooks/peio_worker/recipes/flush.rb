node['peio']['elasticsearch']['indices'].each do |index|
  execute "curl -XDELETE http://localhost/#{index}"
end
