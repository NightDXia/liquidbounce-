package net.ccbluex.liquidbounce.ui.client.hud.element.elements
import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.features.module.modules.player.AutoL
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.Side
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.CPSCounter
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.ServerUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils.color
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11
import tomk.utils.QQUtils
import java.awt.Color
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

@ElementInfo(name = "Session")
 class Session(
    x: Double = 15.0, y: Double = 10.0, scale: Float = 1F,
    side: Side = Side(Side.Horizontal.LEFT, Side.Vertical.UP)
)  : Element(x, y, scale, side) {
    private val blur = BoolValue("Blur", true)
    private val BlurStrength = FloatValue("BlurStrength", 2.6f,0f,20f)
    private val bgValue = IntegerValue("ground-Alpha", 220, 0, 255)
    val shadowValueopen = BoolValue("shadow", true)
    val shadowValue = FloatValue("shadow-Value", 10F, 0f, 20f)
    val shadowColorMode = ListValue("Shadow-Color", arrayOf("Background", "Custom"), "Background")
    private val radiusValue = FloatValue("Radius", 3.1f, 0f, 10f)


    override fun drawElement(): Border? {

        //双重背景
        RenderUtils.drawRoundedRect(
            -5f,
            1F,
            180f,
            16f,
            radiusValue.get(),
            Color(0, 0, 32, bgValue.get()).rgb
        )

        //背景
        RenderUtils.drawRoundedRect(
            -5f,
            1F,
            180f,
            81f,
            radiusValue.get(),
            Color(0, 0, 40, bgValue.get()).rgb
        )
        //shadow
        GL11.glTranslated(-renderX, -renderY, 0.0)
        GL11.glScalef(1F, 1F, 1F)
        GL11.glPushMatrix()
        if (shadowValueopen.get()) {
            tomk.utils.ShadowUtils.shadow(shadowValue.get(), {
                GL11.glPushMatrix()
                GL11.glTranslated(renderX, renderY, 0.0)
                GL11.glScalef(scale, scale, scale)

                RenderUtils.originalRoundedRect(
                    -5f, 1F, 180F, 81F, radiusValue.get(),
                    if (shadowColorMode.get().equals("background", true))
                        Color(32, 30, 30).rgb
                    else
                        Color(0, 0, 0).rgb
                )
                GL11.glPopMatrix()
            }, {
                GL11.glPushMatrix()
                GL11.glTranslated(renderX, renderY, 0.0)
                GL11.glScalef(scale, scale, scale)
                GlStateManager.enableBlend()
                GlStateManager.disableTexture2D()
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
                RenderUtils.fastRoundedRect(-5f, 1F, 180F, 81F, radiusValue.get())
                GlStateManager.enableTexture2D()
                GlStateManager.disableBlend()
                GL11.glPopMatrix()
            }
            )
        }
        GL11.glPopMatrix()
        GL11.glScalef(scale, scale, scale)
        GL11.glTranslated(renderX, renderY, 0.0)
        //blur
        if (blur.get()) {
            GL11.glTranslated(-renderX, -renderY, 0.0)
            GL11.glPushMatrix()
            tomk.utils.BlurBuffer.CustomBlurRoundArea(
                renderX.toFloat() - 5,
                renderY.toFloat() + 1  ,
                185F,
                81F,
                radiusValue.get(), BlurStrength.get()
            )
            GL11.glPopMatrix()
            GL11.glTranslated(renderX, renderY, 0.0)
        }
        val x2 = 120f
        val autoL = LiquidBounce.moduleManager.getModule(AutoL::class.java) as AutoL

        val DATE_FORMAT = SimpleDateFormat("HH:mm:ss")

        Fonts.posterama40.drawCenteredString("Session InFormation", 54F , 4f, Color.WHITE.rgb)
        Fonts.posterama35.drawString("Played For:${DATE_FORMAT.format(Date(System.currentTimeMillis() - Recorder.startTime - 8000L * 3600L))}", -3f, 17f,Color(255,255,255).rgb)
        Fonts.posterama35.drawString("Kill:" + autoL.kills().toString(),-3, 28, Color(255,255,255).rgb)
        Fonts.posterama35.drawString("User:"+mc2.getSession().username,-3,39,Color(255,255,255).rgb)
        Fonts.posterama35.drawString("ServerIp:"+ ServerUtils.getRemoteIp(),-3,50,Color(255,255,255).rgb)
        Fonts.posterama35.drawString("Cps/Fps:"+ CPSCounter.getCPS(CPSCounter.MouseButton.LEFT).toString()+"/"+ Minecraft.getDebugFPS().toString(),-3,61,Color(255,255,255).rgb)
        Fonts.posterama35.drawString("My heart beats faster when I see you.",-3,72,Color(255,255,255).rgb)




        return Border(-5f, 1F, 180f,  81f)
    }
}