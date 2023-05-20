
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        name: "Home",
        component: () => import('pages/IndexPage.vue')
      },
      {
        path: 'Pdf',
        name: "Pdf",
        component: () => import('src/pages/ReportPage.vue')
      },
      {
        path: 'Settings',
        name: "Settings",
        component: () => import('src/pages/SettingsPage.vue')
      },
      {
        path: 'Map',
        name: "Map",
        component: () => import('src/pages/MapPage.vue')
      },
    ]
  },
  { path: '/login', name: 'login', component: () => import('pages/LoginPage.vue') },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }

]

export default routes
