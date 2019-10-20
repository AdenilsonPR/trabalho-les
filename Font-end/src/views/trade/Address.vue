<template>
  <v-app>
    <!-- Toolbar -->
    <TradeToolbar />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout row wrap>
        <v-flex sm9>
          <p class="display-1 text-center mt-5">Endereço</p>

          <!-- Lista de endereços -->
          <v-card flat>
            <v-card v-for="(endereco, index) in enderecos" :key="index" class="mb-1">
              <v-card-text>
                <v-layout row wrap>
                  <v-flex xs12 sm6 md2>
                    <v-text-field label="CEP" v-model="endereco.cep"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md5>
                    <v-text-field label="Logradouro" v-model="endereco.logradouro"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md5>
                    <v-text-field label="Bairro" v-model="endereco.bairro"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md5>
                    <v-text-field label="Cidade" v-model="endereco.cidade"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md4>
                    <v-select
                      :items="['São Paulo', 'Rio de Janeiro', 'Minas Gerais']"
                      label="Estado"
                      v-model="endereco.estado"
                    ></v-select>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-select
                      :items="['Brasil', 'Japão', 'Estados Unidos', 'Canadá']"
                      label="País"
                      v-model="endereco.pais"
                    ></v-select>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-text-field label="Nº" v-model="endereco.numero"></v-text-field>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-select
                      :items="['Casa', 'Apartamento']"
                      label="Tipo de rêsidencia"
                      v-model="endereco.tipoResidencia"
                    ></v-select>
                  </v-flex>
                  <v-flex xs12 sm6 md3>
                    <v-select
                      :items="['Rua', 'Avenida']"
                      label="Tipo de logradouro"
                      v-model="endereco.tipoLogradouro"
                    ></v-select>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-select :items="['Sim', 'Não']" label="Entrega" v-model="endereco.entrega"></v-select>
                  </v-flex>
                  <v-flex xs12 sm6 md2>
                    <v-select :items="['Sim', 'Não']" label="Cobrança" v-model="endereco.cobranca"></v-select>
                  </v-flex>
                </v-layout>
                <v-btn small absolute top right fab class="mt-5" depressed>
                  <v-icon @click="removeEndereco(index)">delete</v-icon>
                </v-btn>
              </v-card-text>
            </v-card>
          </v-card>

          <!-- Endereço vazio -->
          <v-card class="mb-1" v-if="enderecos.length < 2">
            <v-card-text>
              <v-layout row wrap>
                <v-flex sm12 data-cy="enderecos">
                  {{preencherEndereco()}}
                  <v-select
                    v-model="enderecoSelecionado"
                    label="Meus endereços"
                    :items="resumosEnderecosCadastrados"
                    item-value="value"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field label="CEP" v-model="cep" data-cy="cep"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md5>
                  <v-text-field label="Logradouro" v-model="logradouro" data-cy="logradouro"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md5>
                  <v-text-field label="Bairro" v-model="bairro" data-cy="bairro"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md5>
                  <v-text-field label="Cidade" v-model="cidade" data-cy="cidade"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4 data-cy="estado">
                  <v-select
                    :items="['São Paulo', 'Rio de Janeiro', 'Minas Gerais']"
                    label="Estado"
                    v-model="estado"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md3 data-cy="pais">
                  <v-select
                    :items="['Brasil', 'Japão', 'Estados Unidos', 'Canadá']"
                    label="País"
                    v-model="pais"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2>
                  <v-text-field label="Nº" v-model="numero" data-cy="numero"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md3 data-cy="tipo-residencia">
                  <v-select
                    :items="['Casa', 'Apartamento']"
                    label="Tipo de rêsidencia"
                    v-model="tipoResidencia"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md3 data-cy="tipo-logradouro">
                  <v-select
                    :items="['Rua', 'Avenida']"
                    label="Tipo de logradouro"
                    v-model="tipoLogradouro"
                  ></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2 data-cy="entrega">
                  <v-select :items="['Sim', 'Não']" label="Entrega" v-model="entrega"></v-select>
                </v-flex>
                <v-flex xs12 sm6 md2 data-cy="cobranca">
                  <v-select :items="['Sim', 'Não']" label="Cobrança" v-model="cobranca"></v-select>
                </v-flex>
              </v-layout>
            </v-card-text>
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
              <v-icon @click="addEndereco()">done</v-icon>
            </v-btn>
          </v-card>
        </v-flex>

        <!-- Resumo -->
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
                color="deep-orange accent-4"
                block
                v-bind:disabled="!continuar"
                v-bind:dark="continuar"
                @click="irResumo()"
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

export default {
  data: () => ({
    enderecos: [],
    enderecosCadastrados: [],
    resumosEnderecosCadastrados: [],
    enderecoSelecionado: "",
    cep: "",
    logradouro: "",
    numero: "",
    bairro: "",
    cidade: "",
    estado: "",
    pais: "",
    tipoResidencia: "",
    tipoLogradouro: "",
    entrega: "",
    cobranca: ""
  }),

  methods: {
    ...mapMutations(["getEnderecos"]),

    async listarEndereco() {
      let enderecos = await axios.get(
        `/ConsultarEndereco?OPERACAO=CONSULTAR&usuario=${this.stateUsuario.id}`
      );

      enderecos.data.entidades.forEach(endereco => {
        this.resumosEnderecosCadastrados.push({
          value: `${endereco.logradouro} - Nº ${endereco.numero}`,
          text: `${endereco.logradouro} - Nº ${endereco.numero}`
        });
      });

      this.enderecosCadastrados = enderecos.data.entidades;
    },

    addEndereco() {
      this.enderecos.push({
        cep: this.cep,
        logradouro: this.logradouro,
        numero: this.numero,
        bairro: this.bairro,
        cidade: this.cidade,
        estado: this.estado,
        pais: this.pais,
        tipoResidencia: this.tipoResidencia,
        tipoLogradouro: this.tipoLogradouro,
        entrega: this.entrega,
        cobranca: this.cobranca,
        usuario: this.stateUsuario.id
      });
      this.cep = "";
      this.logradouro = "";
      this.numero = "";
      this.bairro = "";
      this.cidade = "";
      this.estado = "";
      this.pais = "";
      this.tipoResidencia = "";
      this.tipoLogradouro = "";
      this.entrega = "";
      this.cobranca = "";
      this.enderecoSelecionado = "";
    },

    removeEndereco(index) {
      this.enderecos.splice(index, 1);
    },

    preencherEndereco() {
      this.enderecosCadastrados.forEach(e => {
        if (this.enderecoSelecionado == `${e.logradouro} - Nº ${e.numero}`) {
          this.cep = e.cep;
          this.logradouro = e.logradouro;
          this.numero = e.numero;
          this.bairro = e.bairro;
          this.cidade = e.cidade;
          this.estado = e.estado;
          this.pais = e.pais;
          this.tipoResidencia = e.tipoResidencia;
          this.tipoLogradouro = e.tipoLogradouro;
          this.entrega = e.entrega;
          this.cobranca = e.cobranca;
        }
      });
    },

    irResumo() {
      this.getEnderecos(this.enderecos);
      this.$router.push({ name: "purchaseSummary" });
    }
  },

  computed: {
    ...mapState(["stateResumoCompra", "stateUsuario"]),

    validarCampos: function() {
      return (
        this.cep != "" &&
        this.logradouro != "" &&
        this.numero != "" &&
        this.bairro != "" &&
        this.cidade != "" &&
        this.estado != "" &&
        this.pais != "" &&
        this.tipoResidencia != "" &&
        this.tipoLogradouro != "" &&
        this.entrega != "" &&
        this.cobranca != ""
      );
    },

    continuar: function() {
      let cobranca = 0;
      let entrega = 0;

      this.enderecos.forEach(endereco => {
        if (endereco.cobranca == "Sim") {
          cobranca++;
        }
        if (endereco.entrega == "Sim") {
          entrega++;
        }
      });

      return this.enderecos.length > 0 && cobranca == 1 && entrega == 1;
    },

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

  created() {
    this.listarEndereco();
  },

  components: {
    TradeToolbar
  }
};
</script>


