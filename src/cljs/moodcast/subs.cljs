(ns moodcast.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :name
 (fn [db]
   (reaction (:name @db))))

(re-frame/register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

(re-frame/register-sub
 :connected?
 (fn [db _]
   (reaction (:connected? @db))))

(re-frame/register-sub
 :people
 (fn [db _]
   (reaction (sort-by :name (remove :disabled? (vals (:people @db)))))))

(re-frame/register-sub
 :svgs
 (fn [db _]
   (reaction (vals (:svgs @db)))))

(re-frame/register-sub
 :scroll
 (fn [db _]
   (reaction (:scroll @db))))
