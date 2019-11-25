<template>

  <section class="questions">
        <swiper :options="swiperOption" ref="questionSwiper" v-on:slideChange="handleSlideChange">
          <swiper-slide v-for="question in questions" :key="question.questionID" :id="question.questionID">

            <div class="learningstate">

              <Learningstates :lsCount="lsCount" :question="question" :learning-states="learningStates"></Learningstates>

              <figure v-if="question.learningState">
                <p>Du beherrscht diese Frage {{ question.learningState.stateName | lower }}</p>
                <img :src="getLsImageUrl(question.learningState)" />
              </figure>

            </div>

            <div class="question-box" v-html="question.question"> </div>
            <div class="answer-box" v-if="showAnswer" v-html="question.answer"> </div>
            <a class="btn btn-primary btn-lg" v-on:click="toggleAnswer">Zeige Antwort</a>

          </swiper-slide>

          <!-- Optional controls -->
          <div class="swiper-pagination"  slot="pagination"></div>
          <div class="swiper-button-prev" slot="button-prev"></div>
          <div class="swiper-button-next" slot="button-next"></div>
          <!--<div class="swiper-scrollbar"   slot="scrollbar"></div>-->

        </swiper>

  </section>
</template>

<script type="application/javascript">


  import { mapState } from 'vuex';
  import 'swiper/dist/css/swiper.css';
  import { swiper, swiperSlide } from 'vue-awesome-swiper';
  import Learningstates from "./Learningstates";

  export default {

    components : {
      Learningstates,
      swiper,
      swiperSlide
    },

    computed: {
      swiper() {
        return this.$refs.questionSwiper.swiper
      },
      ...mapState(['user', 'learningStates', 'questions']),

      // questions: {
      //   get () { return this.$store.state.questions},
      //   set (newValue) { console.log('new questions', newValue) }
      // }

    },

    watch : {
      questions (newq, old) {
        this.lsCount = this.getLearningStatesCount(newq);
      }

    },

    filters: {
      lower: function (value) {
        if (!value) return '';
        return value.toLowerCase()
      }
    },

    data()  {
      return {
        swiperOption: {
          pagination: {
            el: '.swiper-pagination',
            type: 'progressbar'
          },
          navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
          },
          threshold : 25
        },

        showAnswer : false,
        lsCount : {}
      }
    },

    methods : {
      getLsImageUrl(learningState) {
        let item = this.learningStates.find(ls => {
          return learningState ? learningState.learningStateID === ls.learningStateID : false;
        });

        return item.image;
      },

      getLearningStatesCount (questions) {
        let lsCount = {};

        this.learningStates.forEach(ls => {
          lsCount[ls.stateName] = questions.filter(q => {
            return q.learningState && q.learningState.stateName === ls.stateName
          }).length;
        });

        lsCount['other'] = questions.filter(q => {
          return !q.learningState
        }).length;

        return lsCount
      },

      toggleAnswer () {
        this.showAnswer = !this.showAnswer;
      },

      handleSlideChange() {
        //initially hide the answer on every new slide
        this.showAnswer = false;

        //dispatch/emit event active-topic (for handling in parent component _course)
        this.$emit('active-topic', {
          activeTopic : this.questions[this.swiper.activeIndex || 0].topic.topicName
        });
      },
    },

    // beforeMount(){
        // das asnychrone Holen der LS muss in die fetch methode, aber die fetch methode gibt es nur in page components
        // dort wird diese ausgeführt bevor die Comp. gerendert wird
    //   this.$store.dispatch('getLearningStates');
    // },

    mounted (e) {
      // dieses  sorgt dafür, dass beim Laden der ersten Frage im Slider (da das SlideChange Event noch nicht auftrat)
      // dass das active-topic gesetzt wird und so an die Topics Kind-Komponente gegeben wird, dass das aktive Topic gesetzt werden kann
      this.handleSlideChange();
      this.lsCount = this.getLearningStatesCount(this.questions);
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

  .questions .btn-primary {
    color : #fff !important;
  }

  $lsHeight : 65px;

  .swiper-slide {

    position: relative;

    .learningstate {
      height: $lsHeight;
      margin: 40px 0 3px 0;
      padding: 0 25px;

      //display: flex;
      //justify-content: space-between;
      section {
        float: left;


        p {
          color: $color-text-dark;
        }
      }

      figure {
        float: right;
      }

      p {
        margin: 0;
        line-height: 40px;
        margin-right: 15px;
        visibility: hidden;
        display: inline-block;

      }

      img {
        height: 100%;
      }

      &:hover {

        p {
          visibility: visible;
        }
      }
    }

     figure {

       text-align: right;
       height: 40px;

       display: flex;
       justify-content: flex-end;

       margin: 0;
    }
  }

  .swiper-container-horizontal > .swiper-pagination-progressbar,
  .swiper-pagination-progressbar {

    height: 12px;

    .swiper-pagination-progressbar-fill {
      background: $color-menu;
    }
  }


  .swiper-button-next {
    right:0;
    top:85px + $lsHeight;

    width: 0px;
    height: 0px;
    -webkit-transform:rotate(360deg);
    border-style: solid;
    border-width: 28px 0 28px 30px;
    border-color: transparent transparent transparent $color-menu;
  }

  .swiper-button-prev {
    left:0;
    top:85px + $lsHeight;

    background: none;
    width: 0px;
    height: 0px;
    -webkit-transform:rotate(360deg);
    border-style: solid;
    border-width: 28px 30px 28px 0;
    border-color: transparent $color-menu transparent transparent ;
  }


</style>

