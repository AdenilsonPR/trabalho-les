<template>
  <v-app>
    <!-- Toolbar -->
    <TradeToolbar />
    <v-container grid-list-lg>
      <v-layout row wrap>
        <v-flex sm9>
          <!-- Carrinho de compra -->
          <p class="display-1 text-center mt-5">Carrinho de compra</p>
          <v-card v-for="item in itens" :key="item.id" class="mb-1">
            <v-card-text>
              <v-layout row wrap>
                <v-flex sm1>
                  <v-avatar size="60px" tile>
                    <img :src="require('../../assets/vergalhão.jpg')" alt="alt" />
                  </v-avatar>
                </v-flex>
                <v-flex sm5>
                  <span class="ml-5">{{item.descricao}}</span>
                </v-flex>
                <v-flex sm3>
                  <span
                    class="ml-5"
                  >{{item.valorVenda.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm1>
                  <v-text-field
                    name="quantidade"
                    v-model="item.quantidade"
                    type="number"
                    prefix="x"
                  ></v-text-field>
                </v-flex>
                <v-spacer></v-spacer>
                <v-flex sm1>
                  <v-btn icon>
                    <v-icon @click="deleteItem(item.id)">delete</v-icon>
                  </v-btn>
                </v-flex>
              </v-layout>
            </v-card-text>
          </v-card>
        </v-flex>

        <!-- Resumo -->
        <v-flex sm3>
          <p class="display-1 text-center mt-5">Resumo</p>
          <v-card>
            <v-card-text>
              <v-layout row wrap>
                <v-flex sm6>
                  <span>Valor dos produtos:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{calcularValorItens.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm6>
                  <span>Valor do frete:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{calcularFrete.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm12>
                  <v-text-field name="cep" label="CEP" v-model="cep" v-mask="'#####-###'"></v-text-field>
                </v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm6>
                  <h3>Valor total:</h3>
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
                color="deep-orange accent-4"
                block
                v-bind:disabled="!validarCep"
                v-bind:dark="validarCep"
                @click="irPagamento()"
              >Continuar</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
import TradeToolbar from "../../components/trade/TradeToolbar.vue";
import axios from "axios";
import qs from "querystring";
import { mapState, mapMutations } from "vuex";
import { mask } from "vue-the-mask";

export default {
  data: () => ({
    itens: [],
    cep: "",
    frete: 0
  }),

  methods: {
    ...mapMutations(["getCarrinho", "getResumoCompra"]),

    deleteItem(id) {
      this.itens = this.itens.filter(e => e.id != id);
    },

    irPagamento() {
      this.getResumoCompra({
        valorProdutos: this.calcularValorItens,
        valorFrete: this.calcularFrete,
        valorNovoCupom: 0,
        valorDesconto: 0,
        valorTotal: this.valorTotal
      });
      this.$router.push({ name: "makingPayment" });
    },

    async getFrete() {
      let frete = 0;
      await axios
        .post(
          "/SalvarFrete?OPERACAO=SALVAR",
          qs.stringify({ cep: this.cep, valor: 0 })
        )
        .then(function(response) {
          frete = response.data.entidades[0].valor;
        });

      this.frete = frete;
    }
  },

  computed: {
    ...mapState(["stateItens"]),

    calcularValorItens: function() {
      let valorItens = 0;
      this.itens.forEach(item => {
        valorItens += item.valorVenda * item.quantidade;
      });
      return valorItens;
    },

    calcularFrete: function() {
      if (this.cep != "" && this.cep.length == 9) {
        this.getFrete();
        return this.frete;
      } else {
        return 0;
      }
    },
    valorTotal: function() {
      return this.calcularValorItens + this.calcularFrete;
    },

    validarCep: function() {
      return this.cep != "" && this.cep.length == 9 && this.itens.length != 0;
    }
  },

  created() {
    this.itens = this.stateItens;
  },

  components: {
    TradeToolbar
  },

  directives: { mask: mask }
};
</script>


