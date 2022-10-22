<template>
  <div class="flex flex-col gap-4 bg-back text-fore scrollbar h-screen items-center p-8">
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
    </div>
    <div class="flex justify-center text-back gap-2 flex-wrap desktop:text-xl tablet:text-base mobile:text-sm transition-all">
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

    <div class="flex justify-center">
      <div data-aos="slide-up" class="w-1/4 desktop:-mr-64 tablet:-mr-32 mobile:-mr-24 mt-20 transition-all">
        <img alt="main screen" class="phoneanim1 drop-shadow-3xl" src="/third.png" >
      </div>
      <div data-aos="slide-up" data-aos-delay="400" class="w-1/4 desktop:-mr-64 tablet:-mr-32 mobile:-mr-24 mt-10 transition-all">
        <img alt="main screen" class="phoneanim2 drop-shadow-3xl" src="/second.png">
      </div>
      <div data-aos="slide-up" data-aos-delay="800" class="w-1/4 drop-shadow-3xl">
        <img alt="main screen" class="phoneanim3" src="/first.png">
      </div>
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
