# Cooking

Ok, so we need to bootstraps nodes to work with.

Since ansible sucks (and here i lost 90% of my audience), we're gonna 
do it with chef.

Since we don't want to have huge chef server up and running, we will do
it ansible way (goodbye other 9%) - using knife zero.

So we are 1% now, and 99% hate us. But let's continue anyway, this will 
bootstrap local environment:

```
eval "$(chef shell-init bash)"

chef gem install knife-zero
```

Next, let's converge our loaders:

```
cd peio_loader
chef install
chef export -f compiled
cd compiled
// for each target node
knife zero bootstrap --policy-name peio_loader %ip%
```

It's a little bit different for peio_worker, since it requires other 
nodes addresses to be passed:

```
knife zero bootstrap %ip% --policy-name peio_worker --json-attribute-file attributes.json
```

where attributes.json is:

```
{
  "peio_worker": {
    "elasticsearch": {
      "hosts": [%node ips%],
      "memory": "20g"
    }
  }
}

```

You may need `-x root` or `-i %ssh key location%` to succeed.
