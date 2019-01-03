<template>

  <section class="">
        <swiper :options="swiperOption" ref="questionSwiper" v-on:slideChange="slideChange">
          <swiper-slide v-for="question in questions" :key="question.questionID">
          <div class="question-box" v-html="question.question"> </div>
          <div class="answer-box" v-if="showAnswer" v-html="question.answer"> </div>

            <div class="learningState">{{ question.learningState ? question.learningState.stateName : ''  }}</div>
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
          }
        },

        showAnswer : true
      }
    },
    methods : {

      slideChange(e) {

        //dispatch/emit event active-topic (handled in parent component _course)
        this.$emit('active-topic', {
          activeTopic : this.questions[this.swiper.activeIndex].topic.topicName
        });

        //todo: dynamische werte einsetzen
        this.$store.dispatch('getLearningState', {userId : this.$store.state.user.userID, questionId : this.questions[this.swiper.activeIndex].questionID, token : 'Bearer ' + this.$store.state.user.jsonWebToken});
        //console.log(this.$store.state.user.userID);
        //console.log(this.questions[this.swiper.activeIndex]);
        this.$forceUpdate();
      }

    }
  }

</script>

<style lang="scss">

  .question-box, .answer-box {

    padding: 20px;
    margin: 20px 35px;
    color: #fff;

  }

  .question-box {
    background-color: #35495e;

    font-weight: bold;
    font-size: 20px;
  }

  .answer-box {

    background-color: #4b647f;
  }


  .swiper-pagination-progressbar .swiper-pagination-progressbar-fill {
    background: #4b647f;
  }

  /*.swiper-button-next, .swiper-container-rtl .swiper-button-prev {*/

    /*width: 0px;*/
    /*height: 0px;*/
    /*-webkit-transform:rotate(360deg);*/
    /*border-style: solid;*/
    /*border-width: 70px 0 70px 60px;*/
    /*border-color: transparent transparent transparent #4b647f;*/

  /*}*/

  .swiper-button-next {
    right:0;
  }

  .swiper-button-prev {
    left:0;
  }


</style>

