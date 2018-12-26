include_recipe 'peio_parent::setup'
include_recipe 'peio_parent::profiler'

package 'linux-tools-common'
package "linux-tools-#{node['kernel']['release']}"
package "linux-cloud-tools-#{node['kernel']['release']}"
package 'openjdk-8-jdk'

include_recipe 'elasticsearch::default'

elasticsearch_configure 'elasticsearch configuration' do
  allocated_memory node['peio_worker']['elasticsearch']['memory']
  jvm_options %w[
      -XX:+UseConcMarkSweepGC
      -XX:CMSInitiatingOccupancyFraction=75
      -XX:+UseCMSInitiatingOccupancyOnly
      -XX:+AlwaysPreTouch
      -server
      -Xss1m
      -Djava.awt.headless=true
      -Dfile.encoding=UTF-8
      -Djna.nosys=true
      -XX:-OmitStackTraceInFastThrow
      -Dio.netty.noUnsafe=true
      -Dio.netty.noKeySetOptimization=true
      -Dio.netty.recycler.maxCapacityPerThread=0
      -Dlog4j.shutdownHookEnabled=false
      -Dlog4j2.disable.jmx=true
      -XX:+HeapDumpOnOutOfMemoryError
      -XX:+PreserveFramePointer
  ]
  configuration({
      'discovery.zen.ping.unicast.hosts' => node['peio_worker']['elasticsearch']['hosts'],
      'discovery.zen.minimum_master_nodes' => (node['peio_worker']['elasticsearch']['hosts'].size / 2).floor + 1,
      'network.host' => '0.0.0.0'
  })
  action :manage
end

elasticsearch_plugin 'x-pack' do
  action :remove
end

elasticsearch_plugin 'analysis-icu'

elasticsearch_service 'elasticsearch' do
  action :restart
end

cookbook_file '/usr/local/bin/perf.service' do
  mode '0755'
end

sysctl 'kernel.perf_event_paranoid' do
  value 1
end

sysctl 'kernel.kptr_restrict' do
  value 0
end
