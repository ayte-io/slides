include_recipe 'peio_parent::setup'

package 'build-essential'
package 'python3'
package 'python3-dev'
package 'python3-pip'

execute 'pip3 install setuptools'
execute 'pip3 install esrally'

directory '/var/peio'

remote_directory '/var/peio/tracks' do
  source 'tracks'
end
