<template>
  <v-app>
    <TradeToolbar />
    <v-container grid-list-lg>
      <v-layout row wrap>
        <v-flex sm12></v-flex>

        <v-flex sm9>
          <p class="display-1 text-center mt-5">Dados</p>
          <!-- Cartão -->
          <v-card class="mb-1">
            <v-card-title primary-title v-if="stateCartoes.length == 1">Cartão</v-card-title>
            <v-card-title primary-title v-else>Cartões</v-card-title>
            <v-card-text v-for="(cartao, index) in stateCartoes" :key="index">
              <v-layout row wrap>
                <v-flex sm3>
                  <v-text-field label="Nome" v-model="cartao.nome" readonly></v-text-field>
                </v-flex>
                <v-flex sm3>
                  <v-text-field label="Número" v-model="cartao.numero" readonly></v-text-field>
                </v-flex>
                <v-flex sm2>
                  <v-text-field label="Bandeira" v-model="cartao.bandeira" readonly></v-text-field>
                </v-flex>
                <v-flex sm1>
                  <v-text-field label="Código" v-model="cartao.codigo" readonly></v-text-field>
                </v-flex>
                <v-flex sm1>
                  <v-text-field label="Valor" v-model="cartao.valor" readonly></v-text-field>
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
            </v-card-text>
          </v-card>

          <!-- Endereço -->
          <v-card>
            <v-card-title primary-title v-if="stateEnderecos.length == 1">Endereço</v-card-title>
            <v-card-title primary-title v-else>Endereços</v-card-title>
            <v-card-text v-for="endereco in stateEnderecos" :key="endereco.id">
              <v-layout row wrap>
                <v-flex sm2>
                  <v-text-field label="CEP" v-model="endereco.cep" readonly></v-text-field>
                </v-flex>
                <v-flex sm4>
                  <v-text-field label="Logradouro" v-model="endereco.logradouro" readonly></v-text-field>
                </v-flex>
                <v-flex sm1>
                  <v-text-field label="Número" v-model="endereco.numero" readonly></v-text-field>
                </v-flex>
                <v-flex sm1>
                  <v-text-field label="Cobrança" v-model="endereco.cobranca" readonly></v-text-field>
                </v-flex>
                <v-flex sm1>
                  <v-text-field label="Entrega" v-model="endereco.entrega" readonly></v-text-field>
                </v-flex>
              </v-layout>
              <v-divider></v-divider>
            </v-card-text>
          </v-card>
        </v-flex>

        <!-- Resumo da compra -->
        <v-flex sm3>
          <p class="display-1 text-center mt-5">Resumo</p>
          <v-card>
            <v-card-text>
              <v-layout row wrap>
                <v-flex sm6>
                  <span>Valor dos produtos</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{stateResumoCompra.valorProdutos.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm6>
                  <span>Valor do frete</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{stateResumoCompra.valorFrete.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm6>
                  <h3>Valor total</h3>
                </v-flex>
                <v-flex sm6>
                  <h3
                    class="ml-12"
                  >{{valorTotal.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</h3>
                </v-flex>
              </v-layout>
            </v-card-text>
            <v-card-actions>
              <v-btn
                dark
                color="deep-orange accent-4"
                block
                @click="finalizar()"
                data-cy="continuar"
              >Finalizar compra</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
        <v-flex sm10></v-flex>
        <v-spacer></v-spacer>
        <v-flex sm2></v-flex>
      </v-layout>
    </v-container>

    <!-- Modal finalizar compra -->
    <v-dialog v-model="dialog" persistent max-width="500">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Compra finalizada</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text class="mt-5">
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm12>
                <h2 class="text-center">
                  <p>Obrigado pela preferência.</p>
                </h2>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="green darken-1"
            text
            @click="irDashboardTrade()"
            data-cy="confirmar"
          >confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import TradeToolbar from "../../components/trade/TradeToolbar.vue";
import axios from "axios";
import qs from "querystring";
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    dialog: false,
    itens: [],
    venda: {},
    novoCupom: {}
  }),

  methods: {
    ...mapMutations(["getCarrinho"]),

    async finalizar() {
      await this.stateCartoes.forEach(cartao => {
        axios
          .post("/SalvarCartao?OPERACAO=SALVAR", qs.stringify(cartao))
          .then(function(response) {
            console.log();
          })
          .catch(function(error) {
            console.log(error);
          });
      });

      await this.stateEnderecos.forEach(endereco => {
        axios
          .post("/SalvarEndereco?OPERACAO=SALVAR", qs.stringify(endereco))
          .then(function(response) {
            console.log();
          })
          .catch(function(error) {
            console.log(error);
          });
      });

      this.venda.total = this.stateResumoCompra.valorTotal;
      this.venda.usuario = this.stateUsuario.id;

      let idVenda = 0;

      await axios
        .post("/SalvarVenda?OPERACAO=SALVAR", qs.stringify(this.venda))
        .then(function(response) {
          idVenda = response.data.entidades[0].id;
        })
        .catch(function(error) {
          console.log(error);
        });

      await this.stateItens.forEach(item => {
        item.venda = idVenda;
        item.usuario = this.stateUsuario.id;
        item.papel = "Item";
        item.situacao = "Vendido";
        axios
          .post("/SalvarItem?OPERACAO=SALVAR", qs.stringify(item))
          .then(function(response) {
            console.log(response.data);
          })
          .catch(function(error) {
            console.log(error);
          });
      });

      await this.stateCuponsUtilizados.forEach(cupom => {
        axios
          .post(`/ExcluirCupomTroca?OPERACAO=EXCLUIR&id=${cupom.id}`)
          .then(function(response) {
            console.log();
          })
          .catch(function(error) {
            console.log(error);
          });
      });

      this.novoCupom.valor = this.stateResumoCompra.valorNovoCupom;
      this.novoCupom.usuario = this.stateUsuario.id;

      console.log(this.novoCupom);

      if (this.novoCupom.valor > 0) {
        await axios
          .post(
            "/SalvarCupomTroca?OPERACAO=SALVAR",
            qs.stringify(this.novoCupom)
          )
          .then(function(response) {
            console.log();
          })
          .catch(function(error) {
            console.log(error);
          });
      }

      this.dialog = true;
    },

    irDashboardTrade() {
      this.dialog = false;
      this.getCarrinho(this.itens);
      this.$router.push({ name: "dashboardTrade" });
    }
  },

  computed: {
    ...mapState([
      "stateCartoes",
      "stateEnderecos",
      "stateResumoCompra",
      "stateUsuario",
      "stateItens",
      "stateCuponsUtilizados"
    ]),

    valorTotal: function() {
      if (
        this.stateResumoCompra.valorTotal -
          this.stateResumoCompra.valorDesconto <=
        0
      ) {
        return 0;
      } else {
        return (
          this.stateResumoCompra.valorTotal -
          this.stateResumoCompra.valorDesconto
        );
      }
    }
  },
  components: {
    TradeToolbar
  }
};
</script>
