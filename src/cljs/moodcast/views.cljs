(ns moodcast.views
  (:require [clojure.string :as string]
            [re-frame.core :as re-frame]
            [cljs-time.core :as time]
            [cljs-time.format :as timef]
            [goog.string :as gstring]
            [goog.string.format]))


(def formatter-fi (timef/formatter "dd.MM.yyyy"))

(defn fi-now []
  (timef/unparse formatter-fi (time/now)))

#_(defn count-score []
  (let [claims (re-frame/subscribe [:claims])
        data (atom {})]
    (fn []
      [:span (let [as (filter :applies? @claims)
                   c (count (filter :value as))]
               (if (empty? as) "--"
                   (str (gstring/format "%.2f" (* 100 (/ (double c) (count as)))))))])))

;; guide

(defn example [text & content]
  [:div.example (if (map? (first content)) (first content))
   [:h3 text]
   (into [:div.border] (if (map? (first content)) (rest content) content))])

(defn guide-panel []
  [:div.guide
   [:h1 "Component guide"]
   [:h2 "Text"]
   [example "h1" [:h1 "hello"]]
   [example "h2" [:h2 "hello"]]
   [example "h3" [:h3 "hello"]]
   [example "p" [:p "hello"]]
   ])





;; home

(defn person [p]
  [:span.person
   {:class (if (> (:feeling p) 0) :happy :unhappy)}
   (:nick p)
   [:div.photo [:img {:src (:img p)
                      :title (:name p)}]]
   (str "@" (:location p))])

(defn send-msg []
  (re-frame/dispatch [:message :test]))

(defn home-panel []
  (let [people (re-frame/subscribe [:people])]
    (fn []
      [:div
       [:nav.controls
        [:button.secondary.shadow {:on-click send-msg :class "pure-button"} "Test"]]
       #_(into [:div.people]
             (map (fn [p] ^{:key (:id p)} [person p]) @people))])))






;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :guide-panel [] [guide-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])
        connected (re-frame/subscribe [:connected?])]
    (fn []
      [:div
       [:div.content.pure-g {:class (if @connected "connected" "disconnected")}
        [:div.pure-u-1
         [:h1.brand "People"]
         [:div.page (panels @active-panel)]
         ]]
       [:footer
        [:div.pure-g
         [:div.pure-u-1.right
          [:img {:src "img/logo.png"}]]]]])
    ))
