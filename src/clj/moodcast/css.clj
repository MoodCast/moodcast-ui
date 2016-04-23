(ns moodcast.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media at-font-face at-keyframes]]
            [garden.units :as u :refer [px em percent s]]))

(defstyles screen
  [:html {:background-color "#333"
          :padding (em 0)
          :margin (em 1)
          :color "#eee"}]
  [:.avatar
   {:width (px 200)
    :height (px 320)}
   [:.part.sharkman.mask {:left (px 48)
                          :top (px -370)}]
   [:.part.sharkman.mask.normal {:left (px 58)
                                 :top (px -370)}]
   [:.part {:position :relative
            :left (px 0)
            :top (px 0)
            :width (em 2)}]])
