(ns moodcast.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [moodcast.handlers]
              [moodcast.subs]
              [moodcast.routes :as routes]
              [moodcast.views :as views]
              [moodcast.config :as config]
              [moodcast.svg :as svg]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn track-scroll []
  (set! (.-onscroll js/window)
        #(re-frame/dispatch [:scroll (.-pageYOffset js/window)])))

(defn ^:export init [] 
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  ;;(slack/connect)
  (svg/load-avatar :sharkman "img/sharkmanBody.svg" "img/sharkmanMask.svg" "img/sharkmanBodyH.svg" "img/sharkmanMaskH.svg")
  (track-scroll)
  (mount-root))
