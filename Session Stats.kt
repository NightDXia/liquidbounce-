package net.ccbluex.liquidbounce.ui.client.hud.element.elements

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.world.AutoL
import net.ccbluex.liquidbounce.ui.client.hud.element.Border
import net.ccbluex.liquidbounce.ui.client.hud.element.Element
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo
import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.render.RenderUtils
import net.ccbluex.liquidbounce.utils.render.RenderUtils.drawRoundedRect
import net.ccbluex.liquidbounce.value.impl.FloatValue
import net.minecraft.network.handshake.client.C00Handshake
import net.minecraft.network.play.server.SPacketChat
import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*

/**
 * by nightdream 定制 +QQ 2827769469
 */
@ElementInfo(name = "Session Stats")
class SessionStast(x: Double = 10.0, y: Double = 10.0, scale: Float = 1F) : Element(x, y, scale) {
    private val radiusValue = FloatValue("Radius", 3.1f, 0f, 10f)
    val kills = LiquidBounce.moduleManager.getModule(AutoL::class.java) as AutoL
    override fun drawElement(): Border? {
        val playerbans = 0
        RenderUtils.drawRoundedRect(
            -5f,
            1F,
            165f,
            16f,
            radiusValue.get(),
            Color(0, 0, 0, 120).rgb
        )
        RenderUtils.drawRoundedRect(
            -5f,
            1F,
            165f,
            65f,
            radiusValue.get(),
            Color(0, 0, 0, 160).rgb
        )
        Fonts.font20.drawCenteredString("Session Stats", 32F, 5f, Color.WHITE.rgb)
        //
        val playerInfo = mc.netHandler.getPlayerInfo(mc.thePlayer!!.uniqueID)
        if (playerInfo != null) {
            val locationSkin = playerInfo.locationSkin
            drawHead(locationSkin, 40, 40)
        }
        //
        //
        Fonts.font20.drawString("Name",45,21,Color.LIGHT_GRAY.rgb)
        Fonts.font20.drawString(mc2.getSession().username,76,21,Color.WHITE.rgb)
        val DATE_FORMAT = SimpleDateFormat("HH:mm:ss")
        Fonts.font20.drawString("Elapsed", 45, 36,Color.LIGHT_GRAY.rgb)
        Fonts.font20.drawString("${DATE_FORMAT.format(Date(System.currentTimeMillis() - Recorder.startTime - 8000L * 3600L))}", 86, 36,Color(255,255,255).rgb)
        Fonts.font20.drawString("Kill", 45, 51,Color.LIGHT_GRAY.rgb)
        Fonts.font20.drawString( kills.kill.toString(),63,51 ,Color.WHITE.rgb)
        return Border(-5f, 1F, 165f, 65f)
        }

    }
private fun drawHead(skin: IResourceLocation, width: Int, height: Int) {

    MinecraftInstance.mc.textureManager.bindTexture(skin)
    RenderUtils.drawScaledCustomSizeModalRect(1, 19, 8F, 8F, 8, 8, width, height,
        64F, 64F)
}




