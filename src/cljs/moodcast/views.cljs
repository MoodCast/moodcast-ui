(ns moodcast.views
  (:require [clojure.string :as string]
            [re-frame.core :as re-frame]
            [cljs-time.core :as time]
            [cljs-time.format :as timef]
            [goog.string :as gstring]
            [goog.string.format]
            [moodcast.svg :as svg]))


(def formatter-fi (timef/formatter "dd.MM.yyyy"))

(defn fi-now []
  (timef/unparse formatter-fi (time/now)))


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

(def dummy-svg "<svg viewBox=\"0 0 100 100\"><circle style=\"fill:green;\" r=\"100\" cx=\"50\" cy=\"50\"></circle></svg>")

(defn html-div [html-content]
  [:div {:dangerouslySetInnerHTML
         #js{:__html html-content}}])

(defn avatar-part-div [types content]
  [:div.part {:class (string/join " " (map name types))}
   (if (string? content)
     [:div {:dangerouslySetInnerHTML
            #js{:__html content}}]
     content)])

(defn avatar->svg [id state position face]
  (let [avatar (re-frame/subscribe [:avatar id])]
    (fn [id state position]
      (let [body-string (get-in @avatar [:body state])
            face-view [:img {:src face}]
            mask-string (get-in @avatar [:mask state])
            [x y] position]
        (into [:div.avatar {:style {:position :relative :left x :top y}}]
              [[avatar-part-div [id :body state] body-string]
               [avatar-part-div [id :mask state] mask-string]
               [avatar-part-div [id :face state] face-view]
               ])))))

(defn background [id]
  (let [background (re-frame/subscribe [:background id])]
    (fn [id]
      (println @background)
      [:div.background {:class id}
       [html-div @background]])))

(defn person-view [person]
  (println "VIEW" person)
  (if (:avatar person)
    [avatar->svg (:avatar person) (:state person) (:position person) (:face person)]
    [:span]))

(defn home-panel []
  (let [people (re-frame/subscribe [:people])]
    (fn []
      [:div
       [:div.controls [:button {:on-click #(re-frame/dispatch [:state-change (rand-int 1000) (if (< (rand) 0.5) :normal :happy)])} "test"]]
       [background :disco]
       (into [:div.people] (map (fn [p] [person-view p]) @people))
       ])))






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
       [:div.content
        [:div
         [:h1.brand "MoodCast"]
         [:div.page (panels @active-panel)]
         ]]
       ])
    ))
