import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [

    // Trade
    {
      path: '/',
      name: 'dashboardTrade',
      component: () => import('./views/trade/DashboardTrade.vue')
    },
    {
      path: '/MakingSale',
      name: 'makingSale',
      component: () => import('./views/trade/MakingSale.vue')
    },
    {
      path: '/MakingPayment',
      name: 'makingPayment',
      component: () => import('./views/trade/MakingPayment.vue')
    },
    {
      path: '/Address',
      name: 'address',
      component: () => import('./views/trade/Address.vue')
    },
    {
      path: '/PurchaseSummary',
      name: 'purchaseSummary',
      component: () => import('./views/trade/PurchaseSummary.vue')
    },

    // Client
    {
      path: '/DashboardClientData',
      name: 'dashboardClientData',
      component: () => import('./views/client/DashboardClientData.vue')
    },
    {
      path: '/DashboardClientAddress',
      name: 'dashboardClientAddress',
      component: () => import('./views/client/DashboardClientAddress.vue')
    },
    {
      path: '/DashboardClientCard',
      name: 'dashboardClientCard',
      component: () => import('./views/client/DashboardClientCard.vue')
    },
    {
      path: '/DashboardClientShopping',
      name: 'dashboardClientShopping',
      component: () => import('./views/client/DashboardClientShopping.vue')
    },
    {
      path: '/DashboardClientCupom',
      name: 'dashboardClientCupom',
      component: () => import('./views/client/DashboardClientCupom.vue')
    },
    {
      path: '/DashboardClientExchange',
      name: 'dashboardClientExchange',
      component: () => import('./views/client/DashboardClientExchange.vue')
    },

    // Login
    {
      path: '/Login',
      name: 'login',
      component: () => import('./views/login/Login.vue'),
    },
    {
      path: '/NewClient',
      name: 'newClient',
      component: () => import('./views/login/NewClient.vue')
    },
    {
      path: '/NewPassword',
      name: 'newPassword',
      component: () => import('./views/login/NewPassword.vue')
    },
    {
      path: '/ChangePassword',
      name: 'changePassword',
      component: () => import('./views/login/ChangePassword.vue')
    },

    // Admin
    {
      path: '/DashboardAdminClient',
      name: 'dashboardAdminClient',
      component: () => import('./views/admin/DashboardAdminClient.vue')
    },
    {
      path: '/DashboardAdminMaterial',
      name: 'dashboardAdminMaterial',
      component: () => import('./views/admin/DashboardAdminMaterial.vue')
    },
    {
      path: '/DashboardAdminProvider',
      name: 'dashboardAdminProvider',
      component: () => import('./views/admin/DashboardAdminProvider.vue')
    },
    {
      path: '/DashboardAdminTrade',
      name: 'dashboardAdminTrade',
      component: () => import('./views/admin/DashboardAdminTrade.vue')
    },
    {
      path: '/DashboardAdminPrice',
      name: 'dashboardAdminPrice',
      component: () => import('./views/admin/DashboardAdminPrice.vue')
    },
    {
      path: '/DashboardAdminStatistics',
      name: 'dashboardAdminStatistics',
      component: () => import('./views/admin/DashboardAdminStatistics.vue')
    },
    {
      path: '/DashboardAdminExchange',
      name: 'dashboardAdminExchange',
      component: () => import('./views/admin/DashboardAdminExchange.vue')
    },
    {
      path: '/DashboardAdminCupom',
      name: 'dashboardAdminCupom',
      component: () => import('./views/admin/DashboardAdminCupom.vue')
    },
  ]
})