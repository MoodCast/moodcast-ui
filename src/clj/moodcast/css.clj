(ns moodcast.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media at-font-face at-keyframes]]
            [garden.units :as u :refer [px em percent s]]))

(defstyles screen
  [:html {:background-color "#333"
          :padding (em 0)
          :margin (em 1)
          :color "#eee"}]
  [:.brand {:font-size (em 5)
            :font-variant :small-caps
            :text-shadow [[(px 5) (px 5) (px 5) :black]]
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

   [:.part.ironman {:left (px -55)
                    :top (px -350)}]
   [:.part.ironman.mask {:z-index 30}]
   [:.part.ironman.mask.happy {:left (px (- 52 85))
                               :top (px (- -370 350))}]
   [:.part.ironman.mask.normal {:left (px (- 18 55))
                                :top (px (- -370 350))}]
   [:.part.ironman.body.happy {:left (px -85)
                               :top (px -350)}]
   [:.part.ironman.face [:img {:width (px 64)
                               :height (px 64)
                               :border [[(px 1) :solid :red]]}]]
   [:.part.ironman.face.happy {:left (px (- 60 85))
                               :top (px (- -450 350))}]
   [:.part.ironman.face.normal {:left (px (- 30 55))
                                :top (px (- -450 350))}]
   [:.part.ironman.face {:position :relative
                         :z-index 20}]

   [:.part {:position :relative
            :left (px 0)
            :top (px 0)
            :width (em 2)}]])
