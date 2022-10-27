<template>
  <div class="flex flex-col gap-16 scrollbar items-center">
    <div
        class="desktop:text-7xl tablet:text-5xl mobile:text-3xl transition-all"
        data-aos="fade-in"
    >
      HTTPWizard
    </div>
    <div
        class="desktop:text-5xl tablet:text-3xl mobile:text-xl transition-all text-center"
        data-aos="fade-up"
        data-aos-delay="250"
    >
      Little app for API testing made by
      <a class="tganim cursor-pointer" href="https://t.me/ethosa">@Ethosa</a>
      with <span class="tganim">♥</span>

      <!-- methods -->
      <div class="flex mt-4 justify-center text-back gap-2 flex-wrap desktop:text-xl tablet:text-base mobile:text-sm transition-all">
        <span class="bg-red-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="650">GET</span>
        <span class="bg-orange-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="600">POST</span>
        <span class="bg-amber-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="550">PUT</span>
        <span class="bg-yellow-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="500">PATCH</span>
        <span class="bg-lime-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="450">DELETE</span>
        <span class="bg-green-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="400">OPTIONS</span>
        <span class="bg-emerald-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="450">UNLINK</span>
        <span class="bg-teal-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="500">PURGE</span>
        <span class="bg-cyan-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="550">LINK</span>
        <span class="bg-sky-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="600">COPY</span>
        <span class="bg-blue-200 px-4 rounded-full" data-aos="fade-in" data-aos-delay="650">HEAD</span>
      </div>
    </div>

    <!-- Screenshots -->
    <div class="flex justify-center">
      <img alt="main screen" class="h-max phoneanim1" src="/third.png" >
      <img alt="main screen" class="desktop:-ml-64 tablet:-ml-32 mobile:-ml-16 h-max mt-16 phoneanim2 drop-shadow-md" src="/second.png">
      <img alt="main screen" class="desktop:-ml-64 tablet:-ml-32 mobile:-ml-16 h-max phoneanim3 drop-shadow-md" src="/first.png">
    </div>

    <div class="flex flex-col gap-16 items-center w-full">
      <span
          class="w-2/3 text-end desktop:text-2xl tablet:text-xl mobile:text-lg"
          data-aos="fade-right"
      >
        Available for Android on GitHub
      </span>
      <br>
      <br>
      <div class="hover:scale-110 active:scale-90 transition-all">
        <a
            class="relative px-8 py-2 w-fit desktop:text-3xl tablet:text-2xl mobile:text-xl text-back bg-fore rounded-md"
            href="https://github.com/Ethosa/HTTPWizard/releases/download/v0.2.3/HTTPWizard_v0.2.3.apk"
            data-aos="zoom-out"
        >
          Download now
        </a>
      </div>
      <br>
    </div>
  </div>
</template>


<style scoped>
  @keyframes phone {
    0% {
      transform: translateY(-5px);
    }
    100% {
      transform: translateY(5px);
    }
  }
  @keyframes tg {
    0% {
      background-position: 0 82%;
    }
    100% {
      background-position: 19% 100%;
    }
  }
  .tganim {
    background: linear-gradient(
      to right,
      #ef5350,
      #f48fb1,
      #7e57c2,
      #2196f3,
      #26c6da,
      #43a047,
      #eeff41,
      #f9a825,
      #ff5722
    );
    background-size: 1500% 1500%;
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    animation: tg 10s ease-in-out 0s infinite alternate both;
    transition: all;
  }
  .phoneanim1 { animation: 2s phone ease-in-out infinite alternate-reverse }
  .phoneanim2 { animation: 2s phone ease-in-out infinite alternate-reverse; animation-delay: .4s }
  .phoneanim3 { animation: 2s phone ease-in-out infinite alternate-reverse; animation-delay: .8s }
</style>
