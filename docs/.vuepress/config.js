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
      { text: '双引号的全栈笔记', link: 'https://www.fullstackmemo.com', target: '_blank' },
      { text: '实战-前言', link: '/guide/' },
      {
        text: '实战-场景1', items: [
          { text: '前言', link: '/guide/scene_1/' },
          { text: '基础版', link: '/guide/scene_1/base' },
          { text: '进阶版', link: '/guide/scene_1/adv' },
          { text: '再次进阶', link: '/guide/scene_1/adv_2' }
        ]
      },
      {
        text: '实战-场景2', items: [
          { text: '前言', link: '/guide/scene_2/' },
          { text: '基础版', link: '/guide/scene_2/base' },
          { text: '进阶版', link: '/guide/scene_2/adv' }
        ]
      },
      { text: 'GitHub', link: 'https://github.com/x22x22/docker-learn', target: '_blank' },
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
