# Cooking

Ok, so we need to bootstraps nodes to work with.

Since ansible sucks (and here i lost 90% of my audience), we're gonna 
do it with chef.

Since we don't want to have huge chef server up and running, we will do
it ansible way (goodbye other 9%) - using knife zero.

So we are 1% now, and 99% hate us. But let's continue anyway:

```
eval "$(chef shell-init bash)"

chef gem install knife-zero

cd %cookbook%
chef export -f compiled
cd compiled
// for each target node
knife zero bootstrap %ip% --policy-name %cookbook% -x root
```

It's a little bit different for peio_worker, since we want to override
defaults:

```
knife zero bootstrap %ip% --policy-name peio_worker -x root --json-attribute-file attributes.json
```

where attributes.json is:

```
{
  "override": {
    "peio_worker": {
      "elasticsearch": {
        "hosts": [%node ips%],
        "memory": "12g"
      }
    }
  }
}

```
