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
            <v-toolbar-title>Alterar senha</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
          <v-card-text>
            <v-form>
              <v-text-field
                v-model="login.senha"
                label="Senha"
                type="password"
                :rules="regrasSenha"
              ></v-text-field>
              <v-text-field
                v-model="login.confirmacaoSenha"
                label="Confirmação de senha"
                type="password"
                :rules="regrasConfirmacaoSenha"
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn @click="alterar()" color="deep-orange accent-4" dark>Confirmar</v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import axios from "axios";
import qs from "querystring";

export default {
  data: () => ({
    msg: "",
    rota: "",
    login: {
      id: "",
      senha: "",
      confirmacaoSenha: "",
      pessoa: ""
    },

    regrasSenha: [v => !!v || "Senha obrigatória."],
    regrasConfirmacaoSenha: [v => !!v || "Confirmação de senha obrigatório."]
  }),

  methods: {
    async alterar() {
      await axios
        .post("/AlterarLogin?OPERACAO=ALTERAR", qs.stringify(this.login))
        .then(response => {
          this.msg = response.data.mensagem;
          console.log(this.msg);
          this.navigateTo();
        })
        .catch(function(error) {
          console.log(error);
        });
    },

    navigateTo() {
      this.$router.push({
        name: "login",
        query: { msg: this.msg }
      });
    }
  },

  created() {
    let query = location.search.split("&");
    let queryLogin = query[0].split("=");
    let queryCliente = query[0].split("=");
    this.login.id = queryLogin[1];
    this.login.pessoa = queryCliente[1];
  }
};
</script>
