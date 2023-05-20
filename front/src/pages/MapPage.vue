<template>
  <q-page class="flex flex-center">
    <ol-map :loadTilesWhileAnimating="true" :loadTilesWhileInteracting="true"
      style="height:calc(100vh - 82px);width:100vw">
      <ol-view ref="view" :center="center" :rotation="rotation" :zoom="zoom" :projection="projection" />

      <ol-tile-layer>
        <ol-source-osm />
      </ol-tile-layer>
    </ol-map>
  </q-page>
</template>

<script setup>

</script>
