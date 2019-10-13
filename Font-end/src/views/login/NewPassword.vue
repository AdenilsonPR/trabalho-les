<template>
  <v-container fill-height="fill-height">
    <v-layout align-center="align-center" justify-center="justify-center">
      <v-flex xs12 sm8 md4>
        <div class="headline text-uppercase mb-7">
          <span class="display-1 ml-3">Materiais</span>
          <span class="font-weight-light display-1">Construção</span>
        </div>
        <v-card>
          <v-toolbar dark color="deep-orange accent-4" flat>
            <v-toolbar-title>Entrar</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <p>Informe seu e-mail de cadastro e mandaremos um link para a redefinição da senha.</p>
            <v-form>
              <v-text-field label="E-mail" name="email" prepend-icon="person" type="text"></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn :to="{name: 'login'}" color="deep-orange accent-4primary" dark>Confirmar</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  props: {
    source: String
  },
  data: () => ({
    drawer: null
  })
};
</script>
