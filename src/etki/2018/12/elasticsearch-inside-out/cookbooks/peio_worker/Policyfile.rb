name 'peio_worker'

default_source :supermarket

cookbook 'peio_worker', path: '.'

cookbook 'apt'
cookbook 'elasticsearch'
cookbook 'peio_parent', path: '../peio_parent'

run_list 'peio_worker::setup'

named_run_list :install_data, 'peio_worker::install_data'
named_run_list :index, 'peio_worker::index'
