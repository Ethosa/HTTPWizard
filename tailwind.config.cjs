module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx,vue}",
    "./src/*.{js,ts,jsx,tsx,vue}"
  ],
  theme: {
    extend: {
      colors: {
        'back': '#11151E',
        'fore': '#32C59E',
      },
      screens: {
        'mobile': {'max': '640px'},
        'tablet': {'min': '641px', 'max': '1279px'},
        'desktop': {'min': '1280px'},
      }
    },
  },
  plugins: [],
}
