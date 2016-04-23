(ns moodcast.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media at-font-face at-keyframes]]
            [garden.units :as u :refer [px em percent s]]))

(defstyles screen
  [:html {:background-color "#333"
          :padding (em 0)
          :margin (em 1)
          :color "#eee"
          }]
  [:body {:overflow :hidden}]
  [:.brand {:font-size (em 5)
            :font-variant :small-caps
            :text-shadow [[(px 5) (px 5) (px 5) :black]]
            :padding-top (px 100)
            :z-index 1}]
  [:.controls {:z-index 100}]
  [:.background {:position :absolute
                 :left (px 0)
                 :top (px 0)
                 :z-index -1}]

  [:.middle {:width (px 20)
             :height (px 20)
             :background-color "#f00"
             :position :absolute
             :left (px (/ 1920 2))
             :top (px (/ 1080 2))
             :z-index 100}]
  [:.avatar
   {:width (px 200)
    :height (px 320)}
    [:img {:width (px 80)
           :height (px 80)
           }]
   [:.part.body {:position :relative
                 :left (px -100)
                 :top (px -330)}]
   [:.part.mask {:position :relative
                 :left (px -43)
                 :top (px (- -450 330))}]
   [:.part.face {:position :relative
                 :left (px -30)
                 :top (px (- -322 330))}]
   [:.name {:color "#eee"
            :text-shadow "-1px 0 black, 0 1px black, 1px 0 black, 0 -1px black"
            :font-weight :bold
            :font-variant :small-caps
            :display :inline-block
            :font-size (em 1.5)
            :text-align :center
            :margin-left (em -4.6)
            :width (em 10)
            ;;:border "1px solid black"
            :position :relative
            :left (px 0)
            :top (px -1120)}]])
