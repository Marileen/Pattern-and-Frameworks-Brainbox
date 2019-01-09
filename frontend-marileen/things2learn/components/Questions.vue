<template>

  <section class="">
        <swiper :options="swiperOption" ref="questionSwiper" v-on:slideChange="setTopicAndLsImage">
          <swiper-slide v-for="question in questions" :key="question.questionID" :id="question.questionID">

            <div class="learningstate">

              <Learningstates v-bind:question="question" v-bind:learning-states="learningStates" v-on:newLearningstate="updateLearningstate"></Learningstates>

              <figure v-if="question.learningState">
                <p>Du beherrscht diese Frage {{ question.learningState.stateName | lower }}</p>
                <img :src="lsImage" />
              </figure>

            </div>

          <div class="question-box" v-html="question.question"> </div>
          <div class="answer-box" v-if="showAnswer" v-html="question.answer"> </div>

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

    fetch({ store }) {

    },

    computed: {
      swiper() {
        return this.$refs.questionSwiper.swiper
      },
      ...mapState(['user', 'learningStates']),

      questions: {
        get () { return this.$store.state.questions},
        set (newValue) { console.log('new questions', newValue) }
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

        showAnswer : true,
        lsImage : null
      }
    },
    methods : {

      updateLearningstate (evt, data) {
        console.log(evt);
        this.questions = data;
        this.setTopicAndLsImage(evt);
      },

      setTopicAndLsImage(e) {

        console.log('set topic:');
        //console.log(this.questions[this.swiper.activeIndex || 0].topic.topicName);

        //dispatch/emit event active-topic (handled in parent component _course)
        this.$emit('active-topic', {
          activeTopic : this.questions[this.swiper.activeIndex || 0].topic.topicName
        });

        //console.log(this.questions[this.swiper.activeIndex || 0].learningState);
        if (this.questions[this.swiper.activeIndex || 0].learningState) {
          this.setLearningStateImage(this.questions[this.swiper.activeIndex || 0].learningState.learningStateID);
        }

        //this.$store.dispatch('getLearningState', {userId : this.$store.state.user.userID, questionId : this.questions[this.swiper.activeIndex].questionID, token : 'Bearer ' + this.$store.state.user.jsonWebToken});
        //console.log(this.$store.state.user.userID);
        //console.log(this.questions[this.swiper.activeIndex]);
        // this.$forceUpdate();
      },

      async setLearningStateImage (lsId) {

        console.log('setLearningStateImage');
        console.log(lsId);
        try {
          const response = await fetch('http://127.0.0.1:8050/state/' + lsId + '/image', {
            method: 'GET',
            mode: 'cors',
            headers: {
              // 'Authorization': `bearer ${token}`,
              'Content-Type': 'application/json'
            }
          });

          if (response.ok) {

            console.log('ls image ok');
            // todo data image einfügen
            var imgStream = await response.blob();
            console.log(imgStream);
            this.lsImage = URL.createObjectURL(imgStream);
            //this.lsImage = 'data:image/png;base64,' + imgStream

          } else {
            console.log('ls image failed');
          }

        } catch (e) {
          console.log(e)
        }
      }

    },

    beforeMount(){
      // für alle Questions werden einmal initial die LS geholt und an die Kind-Komponente LearningState dann weitergereicht
      // da die Kind-Komponente mehrmals eingebunden wird, würde sonst das store event öfter gefeuert werden, was nicht nötig ist
      this.$store.dispatch('getLearningStates');
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

    .learningstate {
      height: 40px;
      margin: 20px 0 3px 0;

      display: flex;
      justify-content: space-between;

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

    height: 6px;

    .swiper-pagination-progressbar-fill {
      background: $color-menu;
    }
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

