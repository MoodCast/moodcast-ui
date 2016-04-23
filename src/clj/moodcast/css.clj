(ns moodcast.css
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media at-font-face at-keyframes]]
            [garden.units :as u :refer [px em percent s]]))

(defstyles screen
  [:html {:background-color "#333"}])
  )
