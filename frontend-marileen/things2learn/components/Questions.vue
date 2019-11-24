<template>

  <section class="questions">
        <swiper :options="swiperOption" ref="questionSwiper" v-on:slideChange="handleSlideChange">
          <swiper-slide v-for="question in questions" :key="question.questionID" :id="question.questionID">

            <div class="learningstate">

              <Learningstates v-bind:questionsWithoutState="questionsWithoutState" :question="question" :learning-states="learningStates" v-on:newLearningstate="updateLearningstate"></Learningstates>

              <figure v-if="question.learningState">
                <p>Du beherrscht diese Frage {{ question.learningState.stateName | lower }}</p>
                <img :src="lsImage" />
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

      //
      // // dieses watch sorgt dafür, dass beim Laden der ersten Frage im Slider (da das SlideChange Event noch nicht auftrat)
      // // dass das active-topic gesetzt wird und so an die Topics Kind-Komponente gegeben wird, dass das aktive Topic gesetzt werden kann
      // questions (newq, old) {
      //   this.handleSlideChange();
      //   this.showLSImage();
      //
      //   this.getLearningStatesCount();
      //
      // }

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
        },

        showAnswer : false,
        lsImage : null,
        questionsWithoutState :0
      }
    },
    methods : {


      getLearningStatesCount () {

        //todo : gucken welche ls es gibt und dann mappen

        console.log('states')
        console.log(this.learningStates);

        var lsGut = this.learningStates.find(ls => {
          return ls.stateName == 'gut'
        });
        lsGut.count = this.questions.filter(obj => {
          if (obj.learningState) {
            return obj.learningState.stateName == 'gut'
          }
          return false;
        }).length;

        var lsMittel = this.learningStates.find(ls => {
          return ls.stateName == 'mittelmäßig'
        });
        lsMittel.count = this.questions.filter(obj => {
          if (obj.learningState) {
            return obj.learningState.stateName == 'mittelmäßig'
          }
          return false;
        }).length;

        var lsNicht = this.learningStates.find(ls => {
          return ls.stateName == 'noch nicht'
        });
        lsNicht.count = this.questions.filter(obj => {
          if (obj.learningState) {
            return obj.learningState.stateName == 'noch nicht'
          }
          return false;
        }).length;

        this.questionsWithoutState = this.questions.length - lsGut.count - lsMittel.count - lsNicht.count;

        console.log(this.questionsWithoutState);

      },

      toggleAnswer () {
        this.showAnswer = !this.showAnswer;
      },

      showLSImage () {
        //Bild für den Learningstate anhand der gerade angezeigten question abfragen
        if (this.questions[this.swiper.activeIndex || 0].learningState) {               //sofern es einen LS gibt
          this.setLearningStateImage(this.questions[this.swiper.activeIndex || 0].learningState.learningStateID);
        }
      },

      updateLearningstate (evt, data) {

        this.questions = data;
        //since Learningstate may be changed, call the method that show the up-to-date image
        this.showLSImage();

      },

      handleSlideChange() {

        //initially hide the answer on every new slide
        this.showAnswer = false;

        //dispatch/emit event active-topic (for handling in parent component _course)
        this.$emit('active-topic', {
          activeTopic : this.questions[this.swiper.activeIndex || 0].topic.topicName
        });

      },

      async setLearningStateImage (lsId) {

        this.lsImage = await this.$store.dispatch('setLearningStateImage', { lsId : lsId });

      }

    },

    // beforeMount(){
        // das asnychrone Holen der LS muss in die fetch methode, aber die fetch methode gibt es nur in page components
        // dort wird diese ausgeführt bevor die Comp. gerendert wird
    //   this.$store.dispatch('getLearningStates');
    // },

    mounted (e) {
      console.log('questions:', this.questions);

      // dieses  sorgt dafür, dass beim Laden der ersten Frage im Slider (da das SlideChange Event noch nicht auftrat)
      // dass das active-topic gesetzt wird und so an die Topics Kind-Komponente gegeben wird, dass das aktive Topic gesetzt werden kann
      this.handleSlideChange();
      this.showLSImage();
      this.getLearningStatesCount();


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

