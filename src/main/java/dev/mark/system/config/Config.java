package dev.mark.system.config;

import dev.mark.system.core.Loader;

import com.google.gson.annotations.SerializedName;
import dev.mark.system.data.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dev.mark.system.data.WaypointData;
import dev.mark.system.gui.component.CategoryButton;

public class Config {
   @SerializedName("modules")
   private Map<String, ModuleConfig> modules = new HashMap<String, ModuleConfig>();
   @SerializedName("guiPositions")
   private Map<String, Position> guiPositions = new HashMap<String, Position>();
   @SerializedName("hudPositions")
   private Map<String, Position> hudPositions = new HashMap<String, Position>();
   @SerializedName("expandedModules")
   private Map<String, Boolean> expandedModules = new HashMap<String, Boolean>();
   @SerializedName("lastOpenedCategory")
   private String lastOpenedCategory;
   @SerializedName("categoryScrollPositions")
   private Map<String, Float> categoryScrollPositions;
   @SerializedName("openedGroups")
   private Map<String, GroupScrollState> openedGroups;
   @SerializedName("moduleCardScrollPositions")
   private Map<String, Float> moduleCardScrollPositions;
   @SerializedName("favouriteModules")
   private List<String> favouriteModules;
   @SerializedName("friends")
   private List<String> friends;
   @SerializedName("waypoints")
   private List<Waypoint> waypoints;
   @SerializedName("abConfigs")
   private Map<String, ItemListConfig> abConfigs;
   @SerializedName("activeABConfig")
   private String activeABConfig;
   private static String[] AOo1oHdOgBXHEPZt = new String[1];

   public Config() {
      this.lastOpenedCategory = AOo1oHdOgBXHEPZt[0];
      this.categoryScrollPositions = new HashMap<String, Float>();
      this.openedGroups = new HashMap<String, GroupScrollState>();
      this.moduleCardScrollPositions = new HashMap<String, Float>();
      this.favouriteModules = new ArrayList<String>();
      this.friends = new ArrayList<String>();
      this.waypoints = new ArrayList<Waypoint>();
      this.abConfigs = new HashMap<String, ItemListConfig>();
      this.activeABConfig = null;
   }

   public void a() {
   }

   public Map<String, ModuleConfig> b() {
      return this.modules;
   }

   public Map<String, Position> c() {
      return this.guiPositions;
   }

   public Map<String, Position> d() {
      return this.hudPositions;
   }

   public Map<String, Boolean> e() {
      return this.expandedModules;
   }

   public String f() {
      return this.lastOpenedCategory;
   }

   public Map<String, Float> g() {
      return this.categoryScrollPositions;
   }

   public Map<String, GroupScrollState> h() {
      return this.openedGroups;
   }

   public Map<String, Float> i() {
      return this.moduleCardScrollPositions;
   }

   public List<String> j() {
      return this.favouriteModules;
   }

   public List<String> k() {
      return this.friends;
   }

   public List<Waypoint> l() {
      return this.waypoints;
   }

   public Map<String, ItemListConfig> m() {
      return this.abConfigs;
   }

   public String n() {
      return this.activeABConfig;
   }

   public void o(Map<String, ModuleConfig> map) {
      this.modules = map;
   }

   public void p(Map<String, Position> map) {
      this.guiPositions = map;
   }

   public void q(Map<String, Position> map) {
      this.hudPositions = map;
   }

   public void r(Map<String, Boolean> map) {
      this.expandedModules = map;
   }

   public void s(String s) {
      this.lastOpenedCategory = s;
   }

   public void t(Map<String, Float> map) {
      this.categoryScrollPositions = map;
   }

   public void u(Map<String, GroupScrollState> map) {
      this.openedGroups = map;
   }

   public void v(Map<String, Float> map) {
      this.moduleCardScrollPositions = map;
   }

   public void w(List<String> list) {
      this.favouriteModules = list;
   }

   public void x(List<String> list) {
      this.friends = list;
   }

   public void y(List<Waypoint> list) {
      this.waypoints = list;
   }

   public void z(Map<String, ItemListConfig> map) {
      this.abConfigs = map;
   }

   public void A(String s) {
      this.activeABConfig = s;
   }

   static {
      Loader.init(Config.class);
      C();
   }

   private static String B(char[] achar, long i, int j) {
      return null;
   }

   private static void C() {
   }

   public static void guard() {
   }
}
