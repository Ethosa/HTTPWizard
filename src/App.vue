<template>
  <div class="scrollbar text-fore w-full h-screen p-8">
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
