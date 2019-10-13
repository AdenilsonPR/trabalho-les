<template>
  <v-navigation-drawer app clipped>
    <v-list>
      <v-list-item>
        <v-list-item-content>
          <span class="display-1">Meu Perfil</span>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientData' }">
        <v-list-item-action>
          <v-icon>person</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Dados pessoais</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientAddress' }">
        <v-list-item-action>
          <v-icon>home</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Endereços</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientCard' }">
        <v-list-item-action>
          <v-icon>payment</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Cartões</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientShopping' }">
        <v-list-item-action>
          <v-icon>shopping_basket</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Compras</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientExchange'}">
        <v-list-item-action>
          <v-icon>swap_horiz</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Trocas</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardClientCupom'}">
        <v-list-item-action>
          <v-icon>card_giftcard</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Cupons</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>
