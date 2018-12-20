# Running tracks after converge

Tracks are installed in `/var/peio/tracks` directory, so, first of all,
enter that directory.

To launch a single track, you need following command:

```
esrally \
  --target-hosts <comma-separated list of es endpoints> \
  --pipeline benchmark-only \ # since elasticsearch is managed separately
  --track-path <track name> \
  --challenge <challenge name>
```

Since you probably would want to launch this in nohup'd mode, command 
becomes a little bit more complex:

```
nohup \
  esrally \
  --target-hosts <comma-separated list of es endpoints> \
  --pipeline benchmark-only \ # since elasticsearch is managed separately
  --track-path <track name> \
  --challenge <challenge name> \
  > /var/peio/tracks/wildcard/baseline.log 2>&1 \
  &
```
