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

(defn avatar->svg [avatars person-id id state position face]
  ;;(println "RENDER" person-id id (count @people))
  (let [avatar (get avatars id)
        body-string (get-in avatar [:body state])
        face-view [:img {:src (or face "")}]
        mask-string (get-in avatar [:mask state])
        [x y] position]
    (into [:div.avatar {:id person-id :style {:position :absolute :left x :top y}}]
          [[avatar-part-div [id :body state] body-string]
           [avatar-part-div [id :face state] face-view]
           [avatar-part-div [id :mask state] mask-string]
           [:span.name (name person-id)]
           ])))

(defn background [id]
  (let [background (re-frame/subscribe [:background id])]
    (fn [id]
      (println @background)
      [:div.background {:class id}
       [html-div @background]])))

(defn person-view [id]
  (let [avatars (re-frame/subscribe [:avatars])
        person (re-frame/subscribe [:person id])
        people (re-frame/subscribe [:people])]
    (fn [id]
      (println "VIEW" @person (count @people))
      (if (:avatar @person)
        (avatar->svg @avatars (:id @person) (:avatar @person) (:state @person) (:position @person) (:face @person))
        [:span]))))

(defn random-person []
  (let [id (keyword (str "p" (rand-int 1000000)))
        avatar (rand-nth [:ironman :sharkman :catwoman :nahkhiirmees :robin :spiderman])
        state (if (< (rand 0.5)) :normal :happy)]
    (println id avatar state)
    (re-frame/dispatch [:avatar-change id avatar])
    (re-frame/dispatch [:state-change id state])))

(defn home-panel []
  (let [people (re-frame/subscribe [:people])
        avatars (re-frame/subscribe [:avatars])]
    (fn []
      (println "PEOPLE" (count @people))
      (println "AVATARS" (keys @avatars))
      [:div
       [:div.controls
        [:button {:on-click random-person} "random"]]
       ;;[:div.middle "M"]
       [background :disco]
       (into [:div.people] (doall (map (fn [p] [person-view (:id p)]) @people)))
       ])))






;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :guide-panel [] [guide-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [:active-panel])
        connected (re-frame/subscribe [:connected?])
        people (re-frame/subscribe [:people])
        avatars (re-frame/subscribe [:avatars])]
    (fn []
      (println (count @people) (count @avatars))
      [:div
       [:div.content
        [:div
         [:h1.brand "MoodCast"]
         [:div.page (panels @active-panel)]
         ]]
       ])
    ))
