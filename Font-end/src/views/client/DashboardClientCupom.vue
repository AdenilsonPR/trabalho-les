<template>
  <v-app>
    <ClientDrawer />
    <ClientToolbar />
    <v-container grid-list-lg>
      <v-layout wrap>
        <v-flex v-for="cupom in cupons" :key="cupom.id" v-bind="{[`xs${4}`]: true}">
          <v-card>
            <v-card-text>
              <v-layout wrap>
                <v-flex sm8>
                  <v-text-field label="Data da troca" v-model="cupom.dataCadastro" readonly></v-text-field>
                </v-flex>
                <v-spacer></v-spacer>
                <v-flex sm4>
                  <v-text-field label="Valor" v-model="cupom.valor" readonly></v-text-field>
                </v-flex>
              </v-layout>
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
    <ClientFooter />
  </v-app>
</template>

<script>
import ClientDrawer from "../../components/client/ClientDrawer.vue";
import ClientFooter from "../../components/client/ClientFooter.vue";
import ClientToolbar from "../../components/client/ClientToolbar.vue";
import axios from "axios";
import { mapState } from "vuex";

export default {
  data: () => ({
    dialog: false,
    cupons: []
  }),

  methods: {
    async listar() {
      let dadosCupons = await axios.get(
        `/ConsultarCupomTroca?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );
      this.cupons = dadosCupons.data.entidades.filter(
        cupom => cupom.status == "Ativo"
      );
    }
  },

  computed: {
    ...mapState(["stateUsuario"])
  },

  created() {
    this.listar();
  },

  components: {
    ClientDrawer,
    ClientFooter,
    ClientToolbar
  }
};
</script>
