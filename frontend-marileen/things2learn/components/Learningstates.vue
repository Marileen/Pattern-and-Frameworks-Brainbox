<template>

  <section class="learningState">

      <div class="">

        {{ question.learningState ? question.learningState.stateName : ''  }}

        <div class="state-item"></div>

      </div>

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

