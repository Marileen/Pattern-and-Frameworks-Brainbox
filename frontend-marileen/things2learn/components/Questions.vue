<template>

  <section class="">
        <swiper :options="swiperOption" ref="questionSwiper" v-on:slideChange="setTopic">
          <swiper-slide v-for="question in questions" :key="question.questionID" :id="question.questionID">
          <div class="question-box" v-html="question.question"> </div>
          <div class="answer-box" v-if="showAnswer" v-html="question.answer"> </div>

            <div class="learningState" v-if="question.learningState">


              <div class="state-item">
                <!--todo get image-->
                <!--<img :src="question.learningState.ima">-->
                <caption>Ich beherrsche die Frage {{ question.learningState.stateName}}</caption>
              </div>

            </div>

          </swiper-slide>

          <!-- Optional controls -->
          <div class="swiper-pagination"  slot="pagination"></div>
          <div class="swiper-button-prev" slot="button-prev"></div>
          <div class="swiper-button-next" slot="button-next"></div>
          <div class="swiper-scrollbar"   slot="scrollbar"></div>

        </swiper>

  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex';
  import 'swiper/dist/css/swiper.css';
  import { swiper, swiperSlide } from 'vue-awesome-swiper';

  export default {

    components : {
      swiper,
      swiperSlide
    },

    fetch({ store }) {

    },

    computed: {
      swiper() {
        return this.$refs.questionSwiper.swiper
      },
      ...mapState(['questions', 'user']),
    },

    watch : {

      questions (newq, old) {
        this.setTopic();
      }

    },

    data()  {
      return {
        swiperOption: {
          // some swiper options/callbacks
          pagination: {
            el: '.swiper-pagination',
            type: 'progressbar'
          },
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
          },
          //init: this.setTopic,
        },

        showAnswer : true
      }
    },
    methods : {

      setTopic(e) {

        console.log('set topic:');
        //console.log(this.questions[this.swiper.activeIndex || 0].topic.topicName);

        //dispatch/emit event active-topic (handled in parent component _course)
        this.$emit('active-topic', {
          activeTopic : this.questions[this.swiper.activeIndex || 0].topic.topicName
        });

        //this.$store.dispatch('getLearningState', {userId : this.$store.state.user.userID, questionId : this.questions[this.swiper.activeIndex].questionID, token : 'Bearer ' + this.$store.state.user.jsonWebToken});
        //console.log(this.$store.state.user.userID);
        //console.log(this.questions[this.swiper.activeIndex]);
        // this.$forceUpdate();
      }

    },

    beforeMount(){
      //
    },

    mounted () {
      //
    }

  }

</script>

<style lang="scss">

  @import "../assets/colors.scss";

  .question-box, .answer-box {

    padding: 20px;
    margin: 20px 40px;
    //color: #fff;

  }

  .question-box {
    background-color: $color-primary;

    font-weight: bold;
    font-size: 20px;
  }

  .answer-box {

    background-color: $color-secondary;
  }

  .swiper-slide {
    position: relative;
    .state-item {
      position: absolute;
      top:0;
      right: 0;
    }
  }


  .swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #4b647f;
  }


  .swiper-button-next {
    right:0;

    width: 0px;
    height: 0px;
    -webkit-transform:rotate(360deg);
    border-style: solid;
    border-width: 28px 0 28px 30px;
    border-color: transparent transparent transparent $color-menu;
  }

  .swiper-button-prev {
    left:0;

    background: none;
    width: 0px;
    height: 0px;
    -webkit-transform:rotate(360deg);
    border-style: solid;
    border-width: 28px 30px 28px 0;
    border-color: transparent $color-menu transparent transparent ;
  }


</style>

