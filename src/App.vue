<template>
  <div class="scrollbar">
    <router-view/>
  </div>
</template>

<script setup>
import { onMounted } from "vue";
import AOS from "aos";

onMounted(() => {
  AOS.init();
})
</script>
