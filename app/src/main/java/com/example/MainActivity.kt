package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                OmniTierApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OmniTierApp() {
    var selectedItem by remember { mutableStateOf(0) }
    var showPrivacyPanel by remember { mutableStateOf(false) }
    val items = listOf("OmniCore", "Shield", "Studio", "Vault")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.CheckCircle, Icons.Filled.PlayArrow, Icons.Filled.Lock)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = TrueBlack,
        topBar = {
            TopStatusBar()
        },
        bottomBar = {
            ErgonomicBottomSection(
                selectedItem = selectedItem,
                items = items,
                icons = icons,
                onItemSelected = { selectedItem = it },
                onPrivacyClick = { showPrivacyPanel = true }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedItem) {
                0 -> BrowserScreen()
                1 -> ShieldScreen()
                2 -> StudioScreen()
                3 -> VaultScreen()
            }
        }
    }

    if (showPrivacyPanel) {
        ModalBottomSheet(
            onDismissRequest = { showPrivacyPanel = false },
            containerColor = SurfaceDarker
        ) {
            PrivacyControlPanel()
        }
    }
}

@Composable
fun TopStatusBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "OMNI-TIER V1.0.4",
            color = Slate500,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(Color(0xFF121212), CircleShape)
                    .border(1.dp, CyberCyan.copy(alpha = 0.3f), CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(modifier = Modifier.size(6.dp).background(CyberCyan, CircleShape))
                Text(
                    text = "WIREGUARD",
                    color = CyberCyan,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    letterSpacing = 0.sp
                )
            }
            Text(
                text = "12ms",
                color = Slate400,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ErgonomicBottomSection(
    selectedItem: Int,
    items: List<String>,
    icons: List<androidx.compose.ui.graphics.vector.ImageVector>,
    onItemSelected: (Int) -> Unit,
    onPrivacyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp, top = 8.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search / URL Bar (Safari Style)
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Brush.horizontalGradient(listOf(CyberCyan.copy(0.1f), NeuralPurple.copy(0.1f))), RoundedCornerShape(16.dp))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceLight, RoundedCornerShape(16.dp))
                    .border(1.dp, BorderLight, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = CyberCyan,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "omni-tier://secure-search",
                    color = Slate100,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier.width(1.dp).height(24.dp).background(BorderLight)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = Slate400,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Bottom Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, _ ->
                val isSelected = selectedItem == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onItemSelected(index) }
                        .background(if (isSelected) Color.White.copy(0.05f) else Color.Transparent)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = items[index],
                        tint = if (isSelected) CyberCyan else Slate500,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            // Add a 5th element just to match the visual weight of the HTML nav
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onPrivacyClick() }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Privacy Control", tint = Slate500)
                }
            }
        }
    }
}

@Composable
fun BrowserScreen() {
    var isOfflineMode by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 24.dp, bottom = 100.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Offline Mode Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isOfflineMode) NeuralPurple.copy(alpha=0.1f) else SurfaceDarker, RoundedCornerShape(16.dp))
                .border(1.dp, if (isOfflineMode) NeuralPurple.copy(alpha=0.5f) else BorderFaint, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable { isOfflineMode = !isOfflineMode }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isOfflineMode) "OFFLINE MODE ACTIVE" else "NETWORK ONLINE",
                    color = if (isOfflineMode) NeuralPurple else CyberCyan,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = if (isOfflineMode) "Loading exclusively from local encrypted cache." else "Connected via Anycast Edge Nodes.",
                    color = Slate500,
                    fontSize = 12.sp
                )
            }
            Icon(
                imageVector = if (isOfflineMode) Icons.Filled.Lock else Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = if (isOfflineMode) NeuralPurple else CyberCyan
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Intelligent Tab Groups
        Text(
            text = "INTELLIGENT WORKSPACES",
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        // Group 1
        TabGroupCard(
            title = "Research",
            domain = "github.com, arxiv.org",
            tabCount = 8,
            isAutoGrouped = true,
            iconTint = CyberCyan
        )
        Spacer(modifier = Modifier.height(12.dp))
        // Group 2
        TabGroupCard(
            title = "Shopping (Rule: E-Commerce)",
            domain = "amazon.com, ebay.com",
            tabCount = 4,
            isAutoGrouped = false,
            iconTint = CobaltBlue
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Offline Pages Vault
        Text(
            text = "LOCAL OFFLINE VAULT",
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
        )

        OfflinePageItem("Quantum Computing Wiki", "en.wikipedia.org", "2.4 MB")
        Spacer(modifier = Modifier.height(8.dp))
        OfflinePageItem("Rust Documentation", "doc.rust-lang.org", "12.1 MB")
        Spacer(modifier = Modifier.height(8.dp))
        OfflinePageItem("Kubernetes K8s Setup", "kubernetes.io", "4.8 MB")
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun TabGroupCard(title: String, domain: String, tabCount: Int, isAutoGrouped: Boolean, iconTint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ObsidianBlack, RoundedCornerShape(16.dp))
            .border(1.dp, BorderFaint, RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(iconTint.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                .border(1.dp, iconTint.copy(alpha = 0.3f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(tabCount.toString(), color = iconTint, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = title, color = Slate100, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (isAutoGrouped) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.background(CyberCyan.copy(alpha=0.1f), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp)) {
                        Text("AUTO", color = CyberCyan, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Text(text = domain, color = Slate500, fontSize = 12.sp)
        }
    }
}

@Composable
fun OfflinePageItem(title: String, url: String, size: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceLight, RoundedCornerShape(12.dp))
            .border(1.dp, BorderFaint, RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = Slate400, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, color = Slate100, fontSize = 14.sp)
            Text(text = url, color = Slate500, fontSize = 12.sp)
        }
        Text(text = size, color = Slate400, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
    }
}

@Composable
fun ShieldScreen() {
    ThemeDashboardScreen(
        title = "System Armor Active",
        subtitle = "Zero-Trust Backend Engaged. 128 Handshakes Sanitized.",
        icon = Icons.Filled.CheckCircle,
        metric1Title = "Privacy Armor", metric1Value = "99.9%",
        metric2Title = "Threats Dropped", metric2Value = "5,492",
        iconTint = NeuralPurple
    )
}

@Composable
fun StudioScreen() {
    var neuralUpscaling by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            ThemeDashboardScreen(
                title = "4K Media Creator Studio",
                subtitle = "NPU hardware acceleration standing by.",
                icon = Icons.Filled.PlayArrow,
                metric1Title = "NPU Upscaling", metric1Value = if (neuralUpscaling) "ACTIVE" else "OFF",
                metric2Title = "Zero-Copy Mem", metric2Value = "Enabled",
                iconTint = CyberCyan
            )
        }
        Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ObsidianBlack, RoundedCornerShape(16.dp))
                    .border(1.dp, BorderFaint, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Settings, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Neural Upscaling Interface", color = Slate100, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Bypass CPU and route directly to device NPU.", color = Slate500, fontSize = 12.sp)
                }
                Switch(
                    checked = neuralUpscaling,
                    onCheckedChange = { neuralUpscaling = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = CyberCyan, checkedTrackColor = CyberCyan.copy(alpha = 0.3f))
                )
            }
        }
    }
}

@Composable
fun VaultScreen() {
    var isUnlocked by remember { mutableStateOf(false) }

    if (!isUnlocked) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(SurfaceDarker, CircleShape)
                    .border(2.dp, NeuralPurple.copy(alpha = 0.5f), CircleShape)
                    .clickable { isUnlocked = true },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Lock, contentDescription = "Unlock", tint = NeuralPurple, modifier = Modifier.size(48.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text("SECURE ENCLAVE LOCKED", color = NeuralPurple, fontSize = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Tap the padlock to authenticate via Biometrics", color = Slate500, fontSize = 12.sp)
        }
    } else {
        ThemeDashboardScreen(
            title = "Zero-Knowledge Vault",
            subtitle = "E2EE Sync Established. Master keys derived.",
            icon = Icons.Filled.Lock,
            metric1Title = "Post-Quantum", metric1Value = "Secured",
            metric2Title = "Sync Nodes", metric2Value = "24",
            iconTint = NeuralPurple
        )
    }
}

@Composable
fun ThemeDashboardScreen(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    metric1Title: String, metric1Value: String,
    metric2Title: String, metric2Value: String,
    iconTint: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Shield Asset Container (Aesthetics)
        Box(
            modifier = Modifier
                .size(128.dp)
                .background(SurfaceDarker, RoundedCornerShape(24.dp))
                .border(1.dp, iconTint.copy(alpha = 0.5f), RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = iconTint
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = (-0.5).sp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Slate500,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Metric Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricCard(modifier = Modifier.weight(1f), title = metric1Title, value = metric1Value, iconTint = CyberCyan)
            MetricCard(modifier = Modifier.weight(1f), title = metric2Title, value = metric2Value, iconTint = NeuralPurple)
        }
    }
}

@Composable
fun MetricCard(modifier: Modifier = Modifier, title: String, value: String, iconTint: Color) {
    Column(
        modifier = modifier
            .background(ObsidianBlack, RoundedCornerShape(16.dp))
            .border(1.dp, BorderFaint, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title.uppercase(),
            color = Slate500,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = value,
                color = iconTint,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            // Visual decorative element (e.g. progress bar)
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .weight(1f)
                    .background(Color.White.copy(0.05f), CircleShape)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.8f)
                        .background(iconTint, CircleShape)
                )
            }
        }
    }
}

@Composable
fun PrivacyControlPanel() {
    var blockTrackers by remember { mutableStateOf(true) }
    var blockScripts by remember { mutableStateOf(false) }
    var blockCookies by remember { mutableStateOf(true) }
    var showLiveTelemetry by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("PRIVACY & TELEMETRY", color = Slate100, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        
        ToggleRow("Block Third-Party Trackers", "Sandboxing enabled for cross-site IDs.", blockTrackers, CyberCyan) { blockTrackers = it }
        ToggleRow("Block All Scripts", "Disables V8 JIT completely.", blockScripts, NeuralPurple) { blockScripts = it }
        ToggleRow("Per-Site Cookie Isolation", "Store cookies in micro-VM.", blockCookies, CobaltBlue) { blockCookies = it }
        Spacer(modifier = Modifier.height(16.dp))
        ToggleRow("Live VPN Telemetry", "Visualize VPN and CPU throughput.", showLiveTelemetry, CyberCyan) { showLiveTelemetry = it }
        
        if (showLiveTelemetry) {
            Spacer(modifier = Modifier.height(24.dp))
            RealTimeMonitorChart()
        }
    }
}

@Composable
fun ToggleRow(title: String, desc: String, isChecked: Boolean, color: Color, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Slate100, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(desc, color = Slate500, fontSize = 12.sp)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = color, checkedTrackColor = color.copy(alpha = 0.3f))
        )
    }
}

@Composable
fun RealTimeMonitorChart() {
    val points = remember { List(30) { kotlin.random.Random.nextFloat() * 100 } }
    Column(
        modifier = Modifier.fillMaxWidth().height(150.dp).background(ObsidianBlack, RoundedCornerShape(12.dp)).border(1.dp, BorderFaint, RoundedCornerShape(12.dp)).padding(16.dp)
    ) {
        Text("VPN Relay Throughput (Mbps)", color = CyberCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        Spacer(modifier = Modifier.height(16.dp))
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val dx = width / (points.size - 1)
            val path = androidx.compose.ui.graphics.Path().apply {
                moveTo(0f, height - (points[0] / 100) * height)
                for (i in 1 until points.size) {
                    lineTo(i * dx, height - (points[i] / 100) * height)
                }
            }
            drawPath(path = path, color = CyberCyan, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx()))
        }
    }
}
