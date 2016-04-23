(ns moodcast.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media at-font-face at-keyframes]]
            [garden.units :as u :refer [px em percent s]]))

(defstyles screen
  [:html {:background-color "#333"
          :padding (em 0)
          :margin (em 1)
          :color "#eee"}]
  [:.brand {:z-index 1}]
  [:.controls {:z-index 100}]
  [:.background {:position :absolute
                 :left (px 0)
                 :top (px 0)
                 :z-index -1}]
  [:.avatar
   {:width (px 200)
    :height (px 320)}
   [:.part.sharkman.mask {:left (px 48)
                          :top (px -370)
                          :z-index 30}]
   [:.part.sharkman.mask.normal {:left (px 58)
                                 :top (px -370)}]
   [:.part.sharkman.face {:position :relative
                          :left (px 52)
                          :top (px -440)
                          :z-index 20}
    [:img {:width (px 64)
           :height (px 64)
           :border [[(px 1) :solid :red]]}]]
   [:.part.sharkman.face.normal {:position :relative
                                 :left (px 61)
                                 :top (px -440)
                                 :z-index 20}]
   [:.part {:position :relative
            :left (px 0)
            :top (px 0)
            :width (em 2)}]])
