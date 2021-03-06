<template>
  <v-app>
    <!-- Toolbar -->
    <TradeToolbar />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout row wrap>
        <v-flex sm9>
          <p class="display-1 text-center mt-5">Pagamento</p>
          <v-card class="mb-1">
            <v-card-text>
              <v-layout row wrap>
                <!-- Selecionar forma de pagamento -->
                <v-flex sm2 data-cy="tipo-pagamento">
                  <v-select
                    v-model="formaPagamento"
                    label="Forma de pagamento"
                    :items="[{value: 'boleto', text: 'Boleto'}, {value: 'cartao', text: 'Cartão'}]"
                    item-value="value"
                  ></v-select>
                </v-flex>

                <!-- Cupons promocional -->
                <v-flex sm3>
                  <v-text-field label="Cupom promocional" v-model="cupomPromocional"></v-text-field>
                </v-flex>

                <!-- Cupons de desconto -->
                <v-flex sm7 data-cy="cupons">
                  <v-select
                    v-model="cuponsSelecionados"
                    :items="cuponsTroca"
                    item-value="value"
                    label="Cupons de troca"
                    multiple
                    small-chips
                    @click="calcularCuponsTroca"
                  ></v-select>
                </v-flex>
              </v-layout>
            </v-card-text>
          </v-card>

          <!-- Lista de cartões -->
          <v-card v-if="formaPagamento == 'cartao'">
            <v-card v-for="(card, index) in cartoes" :key="index" class="mb-1">
              <v-card-text>
                <v-layout row wrap>
                  <v-flex sm4>
                    <v-text-field label="Nome impresso" v-model="card.nome"></v-text-field>
                  </v-flex>
                  <v-flex sm3>
                    <v-text-field label="Número" v-model="card.numero"></v-text-field>
                  </v-flex>
                  <v-flex sm2>
                    <v-select :items="['Bradesco', 'Elo']" label="Bandeira" v-model="card.bandeira"></v-select>
                  </v-flex>
                  <v-flex sm1>
                    <v-text-field label="Código" v-model="card.codigo"></v-text-field>
                  </v-flex>
                  <v-flex sm2>
                    <v-text-field label="Valor" v-model.lazy="card.valor" v-money="money"></v-text-field>
                  </v-flex>
                </v-layout>
              </v-card-text>
              <v-btn small absolute top right fab class="mt-5" depressed>
                <v-icon @click="removeCartao(index)">delete</v-icon>
              </v-btn>
            </v-card>

            <!-- Cartão vazio -->
            <v-card class="mb-1">
              <v-card-text>
                <v-layout row wrap>
                  <!-- Selecionar cartão cadastrado -->
                  <v-flex sm12 data-cy="cartoes">
                    {{preencherCartao()}}
                    <v-select
                      v-model="cartaoSelecionado"
                      label="Meus cartões"
                      :items="numeroCartoesCadastrados"
                      item-value="value"
                    ></v-select>
                  </v-flex>
                  <v-flex sm4>
                    <v-text-field label="Nome impresso" v-model="nome" data-cy="nome"></v-text-field>
                  </v-flex>
                  <v-flex sm3>
                    <v-text-field label="Número" v-model="numero" data-cy="numero"></v-text-field>
                  </v-flex>
                  <v-flex sm2 data-cy="bandeira">
                    <v-select
                      :items="['Elo', 'Mastercard', 'Visa']"
                      label="Bandeira"
                      v-model="bandeira"
                    ></v-select>
                  </v-flex>
                  <v-flex sm1>
                    <v-text-field label="Código" v-model="codigo" data-cy="codigo"></v-text-field>
                  </v-flex>
                  <v-flex sm2>
                    <v-text-field label="Valor" v-model="valor" v-money="money" data-cy="valor"></v-text-field>
                  </v-flex>
                </v-layout>
              </v-card-text>
            </v-card>

            <v-btn
              v-if="validarCampos"
              small
              absolute
              bottom
              right
              fab
              dark
              color="deep-orange accent-4"
              data-cy="add"
            >
              <v-icon @click="addCartao()">done</v-icon>
            </v-btn>
          </v-card>

          <!-- Boleto -->
          <v-card flat v-if="formaPagamento == 'boleto'">
            <v-card class="mb-1">
              <v-card-text>
                <v-layout row wrap>
                  <v-flex sm8>
                    <v-text-field label="Nome Completo" value="Meu nome"></v-text-field>
                  </v-flex>
                  <v-flex sm4>
                    <v-text-field label="CPF" value="123.123.132-12"></v-text-field>
                  </v-flex>
                </v-layout>
              </v-card-text>
            </v-card>
          </v-card>
        </v-flex>

        <!-- Resumo da compra -->
        <v-flex sm3>
          <p class="display-1 text-center mt-5 mb-6">Resumo</p>
          <v-card>
            <v-card-text>
              <v-layout row wrap>
                <v-flex sm6>
                  <span>Valor dos produtos:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{stateResumoCompra.valorProdutos.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm6>
                  <span>Valor do frete:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{stateResumoCompra.valorFrete.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm6>
                  <span>Valor do desconto:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{calcularCuponsTroca.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm6 v-if="formaPagamento == 'cartao'">
                  <span>Cartão:</span>
                </v-flex>
                <v-flex sm6 v-if="formaPagamento == 'cartao'">
                  <span
                    class="ml-12"
                  >{{somaCartoes.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm12></v-flex>
                <v-flex sm6>
                  <span>Novo cupom:</span>
                </v-flex>
                <v-flex sm6>
                  <span
                    class="ml-12"
                  >{{gerarCupomTroca.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</span>
                </v-flex>
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
                v-bind:disabled="!continuar"
                v-bind:dark="continuar"
                @click="irAddress()"
                data-cy="continuar"
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
import { mapState, mapMutations } from "vuex";
import { VMoney } from "v-money";

export default {
  data: () => ({
    formaPagamento: "cartao",
    cartaoSelecionado: "",
    cartoes: [],
    cuponsTroca: [],
    cuponsSelecionados: [],
    numeroCartoesCadastrados: [],
    cartoesCadastrados: [],
    cuponsCadastrados: [],
    cupomPromocional: "",

    nome: "",
    numero: "",
    bandeira: "",
    codigo: "",
    valor: "",

    money: {
      decimal: ".",
      thousands: "",
      prefix: "R$ ",
      precision: 2,
      masked: false
    }
  }),

  methods: {
    ...mapMutations(["getResumoCompra", "getCartoes", "getCuponsUtilizados"]),

    async listar() {
      let cartoes = await axios.get(
        `/ConsultarCartao?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );
      cartoes.data.entidades.forEach(cartao => {
        if (cartao.status == "Ativo") {
          this.numeroCartoesCadastrados.push({
            value: cartao.numero,
            text: cartao.numero
          });
        }
      });

      console.log(this.numeroCartoesCadastrados);

      this.cartoesCadastrados = cartoes.data.entidades;

      let cupons = await axios.get(
        `/ConsultarCupomTroca?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      cupons.data.entidades.forEach(cupom => {
        if (cupom.status == "Ativo") {
          this.cuponsTroca.push({
            id: cupom.id,
            value: cupom.id + "#" + cupom.valor,
            text: cupom.valor,
            status: cupom.status
          });
        }
      });

      this.cuponsCadastrados = cupons.data.entidades;
    },

    addCartao() {
      this.cartoes.push({
        numero: this.numero,
        nome: this.nome,
        bandeira: this.bandeira,
        codigo: this.codigo,
        valor: this.valor.substring(2, this.valor.length),
        usuario: this.stateUsuario.id
      });

      this.numero = "";
      this.nome = "";
      this.bandeira = "";
      this.codigo = "";
      this.valor = "";
      this.cartaoSelecionado = "";
    },

    removeCartao(index) {
      this.cartoes.splice(index, 1);
    },

    irAddress() {
      this.getResumoCompra({
        valorProdutos: this.stateResumoCompra.valorProdutos,
        valorFrete: this.stateResumoCompra.valorFrete,
        valorDesconto: this.calcularCuponsTroca,
        valorNovoCupom: this.gerarCupomTroca,
        valorTotal: this.stateResumoCompra.valorTotal
      });

      this.getCartoes(this.cartoes);
      this.cuponsUtilizados();
      this.$router.push({ name: "address" });
    },

    preencherCartao() {
      this.cartoesCadastrados.forEach(c => {
        if (c.status == "Ativo") {
          if (this.cartaoSelecionado == c.numero) {
            this.nome = c.nome;
            this.numero = c.numero;
            this.bandeira = c.bandeira;
            this.codigo = c.codigo;
          }
        }
      });
    },

    cuponsUtilizados() {
      let cupons = [];
      this.cuponsCadastrados.forEach(cc => {
        if (cc.status == "Ativo") {
          this.cuponsSelecionados.forEach(cs => {
            let elementos = cs.split("#");
            let id = elementos[0];
            if (id == cc.id) {
              cupons.push(cc);
            }
          });
        }
      });

      this.getCuponsUtilizados(cupons);
    }
  },

  computed: {
    ...mapState(["stateResumoCompra", "stateUsuario"]),

    validarCampos: function() {
      return (
        this.numero != "" &&
        this.nome != "" &&
        this.bandeira != "" &&
        this.codigo != "" &&
        this.valor != "" &&
        this.valor.substring(2, this.valor.length) <= this.valorTotal &&
        (this.valor.substring(2, this.valor.length) >= 10 ||
          this.gerarCupomTroca > 0) &&
        this.somaCartoes <= this.valorTotal
      );
    },

    continuar: function() {
      return this.cartoes.length > 0;
    },

    calcularCuponsTroca: function() {
      let valorCupons = 0;
      this.cuponsSelecionados.forEach(cupom => {
        let elementos = cupom.split("#");
        valorCupons += Number(elementos[1]);
      });

      return valorCupons;
    },

    valorTotal: function() {
      if (this.stateResumoCompra.valorTotal >= this.calcularCuponsTroca) {
        return this.stateResumoCompra.valorTotal - this.calcularCuponsTroca;
      } else {
        return 0;
      }
    },

    gerarCupomTroca: function() {
      if (this.stateResumoCompra.valorTotal < this.calcularCuponsTroca) {
        return this.calcularCuponsTroca - this.stateResumoCompra.valorTotal;
      } else {
        return 0;
      }
    },

    somaCartoes: function() {
      let valor = 0;
      this.cartoes.forEach(cartao => {
        valor += Number(cartao.valor.substring(2, this.valor.length));
      });
      return valor + Number(this.valor.substring(2, this.valor.length));
    },

    // FIXME: cupom promocional tira do novo cupom gerado o valor do proprio cupom promocional
    calcularCupomPromocional: function() {
      if (this.cupomPromocional != "" && this.valorTotal > 10) {
        return 10;
      } else {
        return 0;
      }
    },

    // TODO: Fazer função que calcula se o valor colocado em cada cartão corresponde ao valor total
    calcularValoresCartao: function() {}
  },

  created() {
    this.listar();
  },

  components: {
    TradeToolbar
  },

  directives: { money: VMoney }
};
</script>


