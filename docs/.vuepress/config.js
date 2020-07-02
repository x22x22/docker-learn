module.exports = {
  title: 'Docker实战交流',
  description: '一起来学习交流吧!',
  head: [
    ['link', { rel: 'icon', href: `/images/lemon-violet.png` }]
  ],
  themeConfig: {
    logo: '/images/lemon-violet.png',
    algolia: {
      appId: 'N5QZMAL1RV',
      apiKey: 'e8b3c7b0f465e71badbf5d6eb3650570',
      indexName: 'x22x22-docker-learn'
    },
    nav: [
      { text: 'Home', link: '/' },
      { text: '实战-前言', link: '/guide/' },
      {
        text: '实战-场景1', items: [
          { text: '前言', link: '/guide/scene_1/' },
          { text: '基础版', link: '/guide/scene_1/base' },
          { text: '高阶版', link: '/guide/scene_1/adv' }
        ]
      }
    ],
    displayAllHeaders: true,
    sidebar: 'auto',
  },
  plugins: [
    '@vuepress/active-header-links',
    '@vuepress/back-to-top',
    '@vuepress/nprogress',
    '@vuepress/search',
    '@vuepress/back-to-top',
    '@vuepress/medium-zoom'
  ]
}
