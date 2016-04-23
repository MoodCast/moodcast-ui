(ns moodcast.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [moodcast.handlers]
              [moodcast.subs]
              [moodcast.routes :as routes]
              [moodcast.views :as views]
              [moodcast.config :as config]
              [moodcast.svg :as svg]
              [moodcast.img :as img]
              [moodcast.slack :as slack]))

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
  (slack/connect)
  (svg/load-avatar :sharkman "img/sharkmanBody.svg" "img/sharkmanMask.svg" "img/sharkmanBodyH.svg" "img/sharkmanMaskH.svg")
  (svg/load-avatar :ironman "img/ironmanBody.svg" "img/ironmanMask.svg" "img/ironmanBodyH.svg" "img/ironmanMaskH.svg")
  (svg/load-avatar :catwoman "img/catWomanBody.svg" "img/catWomanMask.svg" "img/catWomanBodyH.svg" "img/catWomanMaskH.svg")
  (svg/load-avatar :nahkhiirmees "img/nahkhiirmeesBody.svg" "img/nahkhiirmeesMask.svg" "img/nahkhiirmeesBodyH.svg" "img/nahkhiirmeesMaskH.svg")
  (svg/load-avatar :robin "img/robinBody.svg" "img/robinMask.svg" "img/robinBodyH.svg" "img/robinMaskH.svg")
  (svg/load-avatar :spiderman "img/spidermanBody.svg" "img/spidermanMask.svg" "img/spidermanBodyH.svg" "img/spidermanMaskH.svg")
  (svg/load-background :disco "img/bg.svg")
  (img/load-face :ile "img/p_Ilkka.png")
  (img/load-face :macroz "img/p_Markku.png")
  (img/load-face :liang "img/p_Liang.png")
  (img/load-face :s27 "img/p_Sofia.png")
  (img/load-face :valeria "img/p_Valeria.png")
  (img/load-face :joseacanada "img/p_Jose.png")
  ;; (re-frame/dispatch [:state-change :macroz :happy])
  ;; (re-frame/dispatch [:state-change :ile :happy])
  ;; (re-frame/dispatch [:state-change :liang :happy])
  ;; (re-frame/dispatch [:state-change :s27 :happy])
  ;; (re-frame/dispatch [:state-change :valeria :happy])
  ;; (re-frame/dispatch [:state-change :joseacanada :happy])
  (track-scroll)
  (mount-root))
