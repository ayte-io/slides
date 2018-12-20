default['peio']['links']['data'] = 'https://dumps.wikimedia.org/other/cirrussearch/20181217/enwikiquote-20181217-cirrussearch-content.json.gz'
default['peio']['links']['profiler'] = 'https://github.com/jvm-profiling-tools/async-profiler/releases/download/v1.4/async-profiler-1.4-linux-x64.tar.gz'
default['peio']['paths']['source'] = '/var/peio/data/index.gz'
default['peio']['paths']['data'] = '/var/peio/data/index'
default['peio']['paths']['profiler'] = '/usr/local/share/async-java-profiler'
default['peio']['elasticsearch']['indices'] = %w[wiki-1 wiki-2 wiki-3 wiki-4 wiki-5]
