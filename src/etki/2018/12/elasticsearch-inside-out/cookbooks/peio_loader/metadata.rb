name 'peio_loader'
maintainer 'Etki'
maintainer_email 'etki@etki.me'
license 'MIT'
description 'Configures loader node'
long_description 'Configures loader node'
version '0.1.0'
chef_version '>= 12.1' if respond_to?(:chef_version)

depends 'poise-python'
depends 'peio_parent'
