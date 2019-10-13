<template>
  <v-navigation-drawer app clipped>
    <v-list>
      <v-list-item>
        <v-list-item-content>
          <span class="display-1">Administação</span>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminClient'}">
        <v-list-item-action>
          <v-icon>person</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Clientes</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminMaterial'}">
        <v-list-item-action>
          <v-icon>work</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Materias</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminProvider'}">
        <v-list-item-action>
          <v-icon>domain</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Fornecedores</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminTrade'}">
        <v-list-item-action>
          <v-icon>reorder</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Vendas</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminExchange'}">
        <v-list-item-action>
          <v-icon>swap_horiz</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Trocas</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminPrice'}">
        <v-list-item-action>
          <v-icon>attach_money</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Precificações</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminCupom'}">
        <v-list-item-action>
          <v-icon>card_giftcard</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Cupons promocionais</v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item :to="{name: 'dashboardAdminStatistics'}">
        <v-list-item-action>
          <v-icon>show_chart</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>Estatisticas</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>


