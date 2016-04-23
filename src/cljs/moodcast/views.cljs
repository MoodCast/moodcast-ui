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
   [:div {:dangerouslySetInnerHTML
          #js{:__html content}}]])

(defn avatar->svg [id state position]
  (let [avatar (re-frame/subscribe [:avatar id])]
    (fn [id state position]
      (let [body-string (get-in @avatar [:body state])
            mask-string (get-in @avatar [:mask state])
            [x y] position]
        (into [:div.avatar {:style {:position :relative :left x :top y}}]
              [(avatar-part-div [id :body] body-string)
               (avatar-part-div [id :mask] mask-string)])))))

(defn person-view [person]
  [avatar->svg (:avatar person) (:state person) (:position person)])

(defn home-panel []
  (let [people (re-frame/subscribe [:people])]
    (fn []
      [:div
       (into [:div.people] (map person-view @people))
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
       [:div.content.pure-g {:class (if @connected "connected" "disconnected")}
        [:div.pure-u-1
         [:h1.brand "MoodCast"]
         [:div.page (panels @active-panel)]
         ]]
       ])
    ))
