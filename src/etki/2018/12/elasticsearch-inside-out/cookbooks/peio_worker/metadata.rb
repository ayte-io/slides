name 'peio_worker'
maintainer 'Etki'
maintainer_email 'etki@etki.me'
license 'MIT'
description 'Configures worker node'
long_description 'Configures worker node'
version '0.1.0'
chef_version '>= 12.1' if respond_to?(:chef_version)

depends 'elasticsearch'
depends 'peio_parent'
