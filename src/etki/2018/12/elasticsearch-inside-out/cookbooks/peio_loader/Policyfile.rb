# Policyfile.rb - Describe how you want Chef to build your system.
#
# For more information on the Policyfile feature, visit
# https://docs.chef.io/policyfile.html

# A name that describes what the system you're building with Chef does.
name "peio_loader"

# Where to find external cookbooks:
default_source :supermarket

# run_list: chef-client will run these recipes in the order specified.
run_list "peio_loader::setup"

# Specify a custom source for a single cookbook:
cookbook "peio_loader", path: "."
cookbook 'peio_parent', path: '../peio_parent'
cookbook 'poise-python'
